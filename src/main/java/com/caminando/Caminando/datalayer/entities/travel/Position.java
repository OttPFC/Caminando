package com.caminando.Caminando.datalayer.entities.travel;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Data
@Entity
@Table(name = "position")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
