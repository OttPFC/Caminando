package com.caminando.Caminando.businesslayer.services.dto.user;

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
    boolean enabled;
    private final List<RoleEntity> roles;

    @Builder(setterPrefix = "with")
    public RegisteredUserDTO(Long id, String firstName, String lastName, String username, String email,boolean enabled, List<RoleEntity> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.roles = roles;
    }
}
