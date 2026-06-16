package com.prakash.jewellers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RateCard {
    private String label;
    private String purity;
    private String unit;
    private Double value;
    private String note;
    private boolean live;
}
