package com.prakash.jewellers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Reel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;

    /** Public Instagram reel/post permalink. */
    @Column(length = 1000)
    private String permalink;

    /** Thumbnail image URL shown in the grid. */
    @Column(length = 1000)
    private String thumbnailUrl;

    private Integer sortOrder = 0;
}
