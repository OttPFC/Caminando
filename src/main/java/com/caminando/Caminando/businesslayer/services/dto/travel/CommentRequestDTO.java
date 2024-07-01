package com.caminando.Caminando.businesslayer.services.dto.travel;


import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import lombok.*;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class CommentRequestDTO extends BaseDTO {

    private String text;
    private LocalDate date;
    private StepResponseDTO step;
    private RegisteredUserDTO user;
}
