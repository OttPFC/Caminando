package com.caminando.Caminando.businesslayer.services.dto.travel;

import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class StepResponseDTO extends BaseDTO {

    private String description;
    private Integer likes;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private TripRequestDTO trip;
    private List<CommentRequestDTO> comments;
    private List<ImageDTO> images;
    private PositionRequestDTO position;
}
