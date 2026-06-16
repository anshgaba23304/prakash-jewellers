package com.prakash.jewellers.service;

import com.prakash.jewellers.dto.GoldRatesResponse;
import com.prakash.jewellers.dto.RateCard;
import com.prakash.jewellers.model.RateConfig;
import com.prakash.jewellers.repository.RateConfigRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Fetches live gold/silver spot prices (USD per troy ounce) from a free,
 * key-less provider and the live USD->INR rate, caches them, and derives the
 * Indian retail rate cards (INR per 10g / per kg) by applying the owner's
 * manually-managed premiums and jewellery prices.
 */
@Service
public class GoldRateService {

    private static final Logger log = LoggerFactory.getLogger(GoldRateService.class);
    private static final double TROY_OUNCE_GRAMS = 31.1034768;

    private final RateConfigRepository rateConfigRepository;
    private final RestClient restClient = RestClient.create();

    @Value("${rates.provider.gold-url}")
    private String goldUrl;
    @Value("${rates.provider.silver-url}")
    private String silverUrl;
    @Value("${rates.provider.fx-url}")
    private String fxUrl;
    @Value("${rates.fallback-usd-inr}")
    private double fallbackUsdInr;
    @Value("${rates.fallback-gold-usd-oz}")
    private double fallbackGoldUsdOz;
    @Value("${rates.fallback-silver-usd-oz}")
    private double fallbackSilverUsdOz;

    // Cached live values
    private volatile double goldUsdOz;
    private volatile double silverUsdOz;
    private volatile double usdInr;
    private volatile boolean liveData = false;
    private volatile Instant lastFetched = Instant.EPOCH;

    public GoldRateService(RateConfigRepository rateConfigRepository) {
        this.rateConfigRepository = rateConfigRepository;
    }

    @PostConstruct
    public void init() {
        this.goldUsdOz = fallbackGoldUsdOz;
        this.silverUsdOz = fallbackSilverUsdOz;
        this.usdInr = fallbackUsdInr;
        refresh();
    }

    @Scheduled(fixedRateString = "${rates.refresh-ms}")
    public void refresh() {
        boolean ok = true;
        try {
            Double gold = fetchPrice(goldUrl);
            if (gold != null && gold > 0) {
                this.goldUsdOz = gold;
            } else {
                ok = false;
            }
        } catch (Exception e) {
            ok = false;
            log.warn("Could not fetch live gold price, using last/fallback. {}", e.getMessage());
        }
        try {
            Double silver = fetchPrice(silverUrl);
            if (silver != null && silver > 0) {
                this.silverUsdOz = silver;
            }
        } catch (Exception e) {
            log.warn("Could not fetch live silver price, using last/fallback. {}", e.getMessage());
        }
        try {
            Double inr = fetchUsdInr(fxUrl);
            if (inr != null && inr > 0) {
                this.usdInr = inr;
            }
        } catch (Exception e) {
            log.warn("Could not fetch USD->INR, using last/fallback. {}", e.getMessage());
        }
        this.liveData = ok;
        this.lastFetched = Instant.now();
        log.info("Rates refreshed (live={}): gold={} USD/oz, silver={} USD/oz, usdInr={}",
                liveData, goldUsdOz, silverUsdOz, usdInr);
    }

    @SuppressWarnings("unchecked")
    private Double fetchPrice(String url) {
        Map<String, Object> body = restClient.get().uri(url).retrieve().body(Map.class);
        if (body == null) return null;
        Object price = body.get("price");
        return price instanceof Number n ? n.doubleValue() : null;
    }

    @SuppressWarnings("unchecked")
    private Double fetchUsdInr(String url) {
        Map<String, Object> body = restClient.get().uri(url).retrieve().body(Map.class);
        if (body == null) return null;
        Object rates = body.get("rates");
        if (rates instanceof Map<?, ?> m) {
            Object inr = m.get("INR");
            return inr instanceof Number n ? n.doubleValue() : null;
        }
        return null;
    }

    /** Live 24KT pure-gold MCX price per 10 grams in INR. */
    public double gold24ktPer10g() {
        return goldUsdOz * usdInr / TROY_OUNCE_GRAMS * 10.0;
    }

    /** Live pure-silver MCX price per kilogram in INR. */
    public double silverPerKg() {
        return silverUsdOz * usdInr / TROY_OUNCE_GRAMS * 1000.0;
    }

    public GoldRatesResponse buildResponse() {
        RateConfig cfg = rateConfigRepository.findById(1L).orElseGet(RateConfig::new);

        double mcx24kt = round(gold24ktPer10g());
        double silverKg = round(silverPerKg());

        List<RateCard> cards = new ArrayList<>();
        cards.add(new RateCard("Gold MCX", "24 KT", "per 10g",
                mcx24kt, "Live from MCX", true));
        cards.add(new RateCard("24 Karat Gold \u2014 Buy Rate", "24 KT", "per 10g",
                round(mcx24kt + nz(cfg.getGoldBuyPremiumPer10g())), "MCX + premium", true));
        cards.add(new RateCard("24 Karat Gold \u2014 Sell Rate", "24 KT", "per 10g",
                round(mcx24kt - nz(cfg.getGoldSellPremiumPer10g())), "MCX - premium", true));
        cards.add(new RateCard("Gold Jewellery", "22 KT", "per 10g",
                round(nz(cfg.getGoldJewellery22ktPer10g())), "Inclusive of making", false));
        cards.add(new RateCard("Gold Jewellery", "20 KT", "per 10g",
                round(nz(cfg.getGoldJewellery20ktPer10g())), "Inclusive of making", false));
        cards.add(new RateCard("Silver Jewellery", "999", "per 10g",
                round(nz(cfg.getSilverJewelleryPer10g())), "Inclusive of making", false));
        cards.add(new RateCard("Pure Silver Bullion", "999", "per kg",
                round(silverKg + nz(cfg.getSilverBullionPremiumPerKg())), "MCX + premium", true));

        GoldRatesResponse resp = new GoldRatesResponse();
        resp.setGoldSpotUsdPerOz(round(goldUsdOz));
        resp.setSilverSpotUsdPerOz(round(silverUsdOz));
        resp.setUsdInr(round(usdInr));
        resp.setLiveData(liveData);
        resp.setUpdatedAt(lastFetched);
        resp.setCards(cards);
        return resp;
    }

    private static double nz(Double d) {
        return d == null ? 0.0 : d;
    }

    private static double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
