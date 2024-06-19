package com.caminando.Caminando.businesslayer.services.dto.travel;


import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.datalayer.entities.travel.Step;
import com.caminando.Caminando.datalayer.entities.travel.User;
import lombok.*;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class CommentDTO extends BaseDTO {

    private String text;
    private LocalDate date;
    private StepDTO step;
    private RegisteredUserDTO user;
}
