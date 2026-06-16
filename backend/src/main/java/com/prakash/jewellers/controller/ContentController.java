package com.prakash.jewellers.controller;

import com.prakash.jewellers.model.Promise;
import com.prakash.jewellers.model.Reel;
import com.prakash.jewellers.model.SiteContent;
import com.prakash.jewellers.repository.PromiseRepository;
import com.prakash.jewellers.repository.ReelRepository;
import com.prakash.jewellers.repository.SiteContentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ContentController {

    private final PromiseRepository promiseRepository;
    private final ReelRepository reelRepository;
    private final SiteContentRepository siteContentRepository;

    public ContentController(PromiseRepository promiseRepository,
                             ReelRepository reelRepository,
                             SiteContentRepository siteContentRepository) {
        this.promiseRepository = promiseRepository;
        this.reelRepository = reelRepository;
        this.siteContentRepository = siteContentRepository;
    }

    @GetMapping("/promises")
    public List<Promise> promises() {
        return promiseRepository.findAllByOrderBySortOrderAsc();
    }

    @GetMapping("/reels")
    public List<Reel> reels() {
        return reelRepository.findAllByOrderBySortOrderAsc();
    }

    @GetMapping("/content")
    public Map<String, String> content() {
        Map<String, String> map = new LinkedHashMap<>();
        for (SiteContent c : siteContentRepository.findAll()) {
            map.put(c.getKey(), c.getValue());
        }
        return map;
    }
}
