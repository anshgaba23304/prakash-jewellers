package com.prakash.jewellers.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Promise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    /** Key matched to an SVG icon on the frontend (e.g. fair-pricing, exchange). */
    private String icon;

    private Integer sortOrder = 0;
}
