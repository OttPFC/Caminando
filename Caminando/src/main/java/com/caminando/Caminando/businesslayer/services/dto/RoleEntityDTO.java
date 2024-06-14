package com.caminando.Caminando.businesslayer.services.dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class RoleEntityDTO extends BaseDTO{
    private String roleType;
}
