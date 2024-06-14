package com.caminando.Caminando.datalayer.entities.travel;

import com.caminando.Caminando.datalayer.entities.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "step")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder(setterPrefix = "with")
public class Step extends BaseEntity{

    private String name;
    private String description;
    private Integer likes;
    private double latitude;
    private double longitude;
    private LocalDate arrivalDate;
    private LocalDate departureDate;

    @ManyToOne
    private Trip trip;

    @OneToMany(mappedBy = "step")
    private List<Comment> comments;

    @OneToMany(mappedBy = "step")
    private List<Image> images;
}
