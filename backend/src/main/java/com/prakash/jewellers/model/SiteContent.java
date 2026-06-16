package com.prakash.jewellers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Generic key/value store for editable site copy (taglines, contact details,
 * address, store hours, etc.).
 */
@Entity
@Table(name = "site_content")
@Data
public class SiteContent {

    @Id
    @Column(name = "content_key")
    private String key;

    @Column(name = "content_value", length = 2000)
    private String value;
}
