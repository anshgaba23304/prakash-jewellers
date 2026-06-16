package com.prakash.jewellers.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.Instant;

/**
 * Manually-managed pricing parameters that the shop owner adjusts 2-3 times a day.
 * Premiums are applied on top of the live MCX spot price; jewellery rates are set
 * directly (inclusive of making charges). Single-row table (id = 1).
 */
@Entity
@Data
public class RateConfig {

    @Id
    private Long id = 1L;

    /** Added to live 24KT MCX (per 10g) to get the customer BUY rate. */
    private Double goldBuyPremiumPer10g = 1500.0;

    /** Subtracted from live 24KT MCX (per 10g) to get the SELL / buy-back rate. */
    private Double goldSellPremiumPer10g = 800.0;

    /** Manual price per 10g, inclusive of making charges. */
    private Double goldJewellery22ktPer10g = 0.0;

    /** Manual price per 10g, inclusive of making charges. */
    private Double goldJewellery20ktPer10g = 0.0;

    /** Manual silver jewellery price per 10g, inclusive of making charges. */
    private Double silverJewelleryPer10g = 0.0;

    /** Premium (per kg) added to live silver MCX for pure bullion. */
    private Double silverBullionPremiumPerKg = 5000.0;

    private Instant updatedAt = Instant.now();
}
