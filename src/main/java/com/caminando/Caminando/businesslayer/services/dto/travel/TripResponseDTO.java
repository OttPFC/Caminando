package com.caminando.Caminando.businesslayer.services.dto.travel;

import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.datalayer.entities.enums.Privacy;
import com.caminando.Caminando.datalayer.entities.enums.Status;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class TripResponseDTO extends BaseDTO {

    private Long id;
    private String title;
    private String description;
    private Integer likes;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
    private Privacy privacy;
    private RegisteredUserDTO user;
    private List<StepRequestDTO> steps;
    private ImageDTO coverImage;

}
