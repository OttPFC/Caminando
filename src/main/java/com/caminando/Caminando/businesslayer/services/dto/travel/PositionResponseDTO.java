package com.caminando.Caminando.businesslayer.services.dto.travel;

import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class PositionResponseDTO extends BaseDTO {

    private Long id;
    private double latitude;
    private double longitude;
    private LocalDate timestamp;
    private String nomeLocalita;
    @JsonIgnore
    private StepResponseDTO step;
    @JsonIgnore
    private RegisteredUserDTO user;

}
