package com.caminando.Caminando.datalayer.entities.travel;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_entity_seq")
    @SequenceGenerator(name = "base_entity_seq", sequenceName = "base_entity_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();

}
