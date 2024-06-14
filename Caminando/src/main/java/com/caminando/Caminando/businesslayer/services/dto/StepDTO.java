package com.caminando.Caminando.businesslayer.services.dto;

import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.travel.Comment;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class StepDTO extends BaseDTO{
    private String name;
    private String description;
    private Integer likes;
    private double latitude;
    private double longitude;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private Trip trip;
    private List<Comment> comments;
    private List<Image> images;
}
