package com.caminando.Caminando.businesslayer.services.dto.travel;

import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import lombok.*;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class PositionResponseDTO extends BaseDTO {

    private double latitude;
    private double longitude;
    private Instant timestamp;
    private String nomeLocalita;
    private StepResponseDTO step;
    private RegisteredUserDTO user;

}
