package com.caminando.Caminando.businesslayer.services.dto.travel;


import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import lombok.*;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class PositionDTO extends BaseDTO {

    private double latitude;
    private double longitude;
    private Instant timestamp;
    private String nomeLocalita;

}
