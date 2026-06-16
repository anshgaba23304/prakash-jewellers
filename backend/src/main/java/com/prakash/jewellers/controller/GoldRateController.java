package com.prakash.jewellers.controller;

import com.prakash.jewellers.dto.GoldRatesResponse;
import com.prakash.jewellers.model.RateConfig;
import com.prakash.jewellers.repository.RateConfigRepository;
import com.prakash.jewellers.service.GoldRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/rates")
public class GoldRateController {

    private final GoldRateService goldRateService;
    private final RateConfigRepository rateConfigRepository;

    public GoldRateController(GoldRateService goldRateService,
                              RateConfigRepository rateConfigRepository) {
        this.goldRateService = goldRateService;
        this.rateConfigRepository = rateConfigRepository;
    }

    /** Public endpoint consumed by the website. */
    @GetMapping
    public GoldRatesResponse getRates() {
        return goldRateService.buildResponse();
    }

    /** Force an immediate re-fetch of live spot prices. */
    @PostMapping("/refresh")
    public GoldRatesResponse refresh() {
        goldRateService.refresh();
        return goldRateService.buildResponse();
    }

    /** Owner: read current premium / jewellery configuration. */
    @GetMapping("/config")
    public RateConfig getConfig() {
        return rateConfigRepository.findById(1L).orElseGet(() -> {
            RateConfig c = new RateConfig();
            return rateConfigRepository.save(c);
        });
    }

    /** Owner: update premiums and manual jewellery rates (2-3 times a day). */
    @PutMapping("/config")
    public ResponseEntity<GoldRatesResponse> updateConfig(@RequestBody RateConfig incoming) {
        RateConfig cfg = rateConfigRepository.findById(1L).orElseGet(RateConfig::new);
        cfg.setId(1L);
        if (incoming.getGoldBuyPremiumPer10g() != null)
            cfg.setGoldBuyPremiumPer10g(incoming.getGoldBuyPremiumPer10g());
        if (incoming.getGoldSellPremiumPer10g() != null)
            cfg.setGoldSellPremiumPer10g(incoming.getGoldSellPremiumPer10g());
        if (incoming.getGoldJewellery22ktPer10g() != null)
            cfg.setGoldJewellery22ktPer10g(incoming.getGoldJewellery22ktPer10g());
        if (incoming.getGoldJewellery20ktPer10g() != null)
            cfg.setGoldJewellery20ktPer10g(incoming.getGoldJewellery20ktPer10g());
        if (incoming.getSilverJewelleryPer10g() != null)
            cfg.setSilverJewelleryPer10g(incoming.getSilverJewelleryPer10g());
        if (incoming.getSilverBullionPremiumPerKg() != null)
            cfg.setSilverBullionPremiumPerKg(incoming.getSilverBullionPremiumPerKg());
        cfg.setUpdatedAt(Instant.now());
        rateConfigRepository.save(cfg);
        return ResponseEntity.ok(goldRateService.buildResponse());
    }
}
