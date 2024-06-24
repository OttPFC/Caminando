package com.caminando.Caminando.businesslayer.services.dto.user;

import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class RoleEntityDTO extends BaseDTO {
    private String roleType;
}
