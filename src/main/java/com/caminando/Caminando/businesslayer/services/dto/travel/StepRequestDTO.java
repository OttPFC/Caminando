package com.caminando.Caminando.businesslayer.services.dto.travel;

import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageResponseDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class StepRequestDTO extends BaseDTO {

    private String description;
    private Integer likes;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private TripResponseDTO trip;
    private List<CommentResponseDTO> comments;
    private List<ImageResponseDTO> images;
    private PositionResponseDTO position;
}
