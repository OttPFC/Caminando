package com.caminando.Caminando.businesslayer.services.dto.travel;

import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class StepDTO extends BaseDTO {

    private String description;
    private Integer likes;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private TripDTO trip;
    private List<CommentDTO> comments;
    private List<ImageDTO> images;
    private PositionDTO position;
}
