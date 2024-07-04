package com.caminando.Caminando.businesslayer.services.dto.travel;


import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class PositionRequestDTO extends BaseDTO {

    private double latitude;
    private double longitude;
    private LocalDate timestamp;
    private String nomeLocalita;
    private StepResponseDTO step;
    private RegisteredUserDTO user;

}
