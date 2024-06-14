package com.caminando.Caminando.businesslayer.services.dto;

import com.caminando.Caminando.datalayer.entities.RoleEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class LoginResponseDTO {
    Long id;
    String firstName;
    String lastName;
    String username;
    String email;
    private final List<RoleEntity> roles;
    String token;

    @Builder(setterPrefix = "with")
    public LoginResponseDTO(List<RoleEntity> roles, Long id, String firstName, String lastName, String username, String email, String token) {
        this.roles = roles;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.token = token;
    }
}
