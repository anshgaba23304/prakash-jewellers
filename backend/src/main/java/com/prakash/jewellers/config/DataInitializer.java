package com.prakash.jewellers.config;

import com.prakash.jewellers.model.Promise;
import com.prakash.jewellers.model.RateConfig;
import com.prakash.jewellers.model.Reel;
import com.prakash.jewellers.model.SiteContent;
import com.prakash.jewellers.repository.PromiseRepository;
import com.prakash.jewellers.repository.RateConfigRepository;
import com.prakash.jewellers.repository.ReelRepository;
import com.prakash.jewellers.repository.SiteContentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RateConfigRepository rateConfigRepository;
    private final PromiseRepository promiseRepository;
    private final ReelRepository reelRepository;
    private final SiteContentRepository siteContentRepository;

    public DataInitializer(RateConfigRepository rateConfigRepository,
                           PromiseRepository promiseRepository,
                           ReelRepository reelRepository,
                           SiteContentRepository siteContentRepository) {
        this.rateConfigRepository = rateConfigRepository;
        this.promiseRepository = promiseRepository;
        this.reelRepository = reelRepository;
        this.siteContentRepository = siteContentRepository;
    }

    private static final String INSTAGRAM_URL = "https://www.instagram.com/prakash.jewellers_/";

    @Override
    public void run(String... args) {
        seedRateConfig();
        seedPromises();
        seedReels();
        seedContent();
        syncReelLinks();
    }

    private void seedRateConfig() {
        if (rateConfigRepository.findById(1L).isEmpty()) {
            RateConfig cfg = new RateConfig();
            cfg.setId(1L);
            // Sensible starting values based on the owner's reference board.
            cfg.setGoldJewellery22ktPer10g(144900.0);
            cfg.setGoldJewellery20ktPer10g(132000.0);
            cfg.setSilverJewelleryPer10g(3200.0);
            rateConfigRepository.save(cfg);
        }
    }

    private void seedPromises() {
        if (promiseRepository.count() > 0) return;
        promiseRepository.saveAll(List.of(
                promise("Fair Pricing",
                        "Transparent pricing you can confidently rely on, every single day.",
                        "fair-pricing", 1),
                promise("Assured Exchange",
                        "The highest, most maximum value for your old jewellery \u2014 guaranteed.",
                        "exchange", 2),
                promise("Endless Choice",
                        "Jewellery crafted to suit every taste, every occasion and every celebration.",
                        "choice", 3),
                promise("Lasting Relationships",
                        "A jewellery experience rooted in trust and long-term relationships.",
                        "relationship", 4)
        ));
    }

    private void seedReels() {
        if (reelRepository.count() > 0) return;
        reelRepository.saveAll(List.of(
                reel("For the one whose hands wrap us in warmth & selfless love",
                        INSTAGRAM_URL,
                        "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?w=600&q=80&auto=format&fit=crop",
                        1),
                reel("Bridal sets crafted to make every celebration unforgettable",
                        INSTAGRAM_URL,
                        "https://images.unsplash.com/photo-1611591437281-460bfbe1220a?w=600&q=80&auto=format&fit=crop",
                        2),
                reel("Timeless gold that stays with you, always",
                        INSTAGRAM_URL,
                        "https://images.unsplash.com/photo-1605100804763-247f67b3557e?w=600&q=80&auto=format&fit=crop",
                        3)
        ));
    }

    private void seedContent() {
        putIfAbsent("brand.name", "Prakash Jewellers");
        putIfAbsent("brand.tagline", "JEWELLERS");
        putIfAbsent("hero.titleHi", "\u090f\u0915 \u0928\u093e\u092e, \u0935\u093f\u0936\u094d\u0935\u093e\u0938 \u0915\u093e");
        putIfAbsent("hero.subtitle", "BUILT ON TRUST SINCE 1999.");
        put("instagram.handle", "@prakash.jewellers_");
        put("instagram.url", INSTAGRAM_URL);
        put("contact.phone", "+91-8279809028");
        put("contact.whatsapp", "918279809028");
        putIfAbsent("contact.email", "customercare@prakashjewellers.com");
        putIfAbsent("store.name", "Prakash Jewellers");
        put("store.address", "Deoband 247554 Saharanpur U.P");
        putIfAbsent("store.hours", "10:00 AM to 8:00 PM on all days");
    }

    private void syncReelLinks() {
        for (Reel reel : reelRepository.findAll()) {
            reel.setPermalink(INSTAGRAM_URL);
            reelRepository.save(reel);
        }
    }

    private void put(String key, String value) {
        SiteContent c = siteContentRepository.findById(key).orElseGet(SiteContent::new);
        c.setKey(key);
        c.setValue(value);
        siteContentRepository.save(c);
    }

    private void putIfAbsent(String key, String value) {
        if (siteContentRepository.findById(key).isEmpty()) {
            SiteContent c = new SiteContent();
            c.setKey(key);
            c.setValue(value);
            siteContentRepository.save(c);
        }
    }

    private Promise promise(String title, String desc, String icon, int order) {
        Promise p = new Promise();
        p.setTitle(title);
        p.setDescription(desc);
        p.setIcon(icon);
        p.setSortOrder(order);
        return p;
    }

    private Reel reel(String caption, String permalink, String thumb, int order) {
        Reel r = new Reel();
        r.setCaption(caption);
        r.setPermalink(permalink);
        r.setThumbnailUrl(thumb);
        r.setSortOrder(order);
        return r;
    }
}
