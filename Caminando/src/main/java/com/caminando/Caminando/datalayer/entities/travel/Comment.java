package com.caminando.Caminando.datalayer.entities.travel;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder(setterPrefix = "with")
public class Comment extends BaseEntity{

    private String text;
    private LocalDate date;

    @ManyToOne
    private Step step;

    @ManyToOne
    private User user;
}
