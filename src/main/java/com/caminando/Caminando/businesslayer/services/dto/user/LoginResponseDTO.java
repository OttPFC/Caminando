package com.caminando.Caminando.businesslayer.services.dto.user;

import com.caminando.Caminando.datalayer.entities.RoleEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class LoginResponseDTO {
    RegisteredUserDTO user;
    String accessToken;

    @Builder(setterPrefix = "with")
    public LoginResponseDTO(RegisteredUserDTO user, String accessToken) {
        this.user = user;
        this.accessToken = accessToken;
    }
}
