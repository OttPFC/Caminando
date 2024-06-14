package com.caminando.Caminando.datalayer.entities.travel;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_entity_seq")
    @SequenceGenerator(name = "base_entity_seq", sequenceName = "base_entity_sequence", allocationSize = 1)
    private Long id;
}
