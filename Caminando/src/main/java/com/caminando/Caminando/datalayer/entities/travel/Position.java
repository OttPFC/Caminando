package com.caminando.Caminando.datalayer.entities.travel;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@Entity
@Table(name = "position")
@EqualsAndHashCode(callSuper = true)
public class Position extends BaseEntity {

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    private Instant timestamp;

    @Column(length = 50, nullable = false)
    private String nomeLocalita;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Step step;
}
