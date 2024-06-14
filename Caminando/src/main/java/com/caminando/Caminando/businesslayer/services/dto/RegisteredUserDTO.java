package com.caminando.Caminando.businesslayer.services.dto;

import com.caminando.Caminando.datalayer.entities.RoleEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class RegisteredUserDTO {
    Long id;
    String firstName;
    String lastName;
    String username;
    String email;
    private final List<RoleEntity> roles;

    @Builder(setterPrefix = "with")
    public RegisteredUserDTO(Long id, String firstName, String lastName, String username, String email, List<RoleEntity> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
