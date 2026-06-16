package com.prakash.jewellers.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class GoldRatesResponse {
    private double goldSpotUsdPerOz;
    private double silverSpotUsdPerOz;
    private double usdInr;
    private boolean liveData;
    private Instant updatedAt;
    private List<RateCard> cards;
}
