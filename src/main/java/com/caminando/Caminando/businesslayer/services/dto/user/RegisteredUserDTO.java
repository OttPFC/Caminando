package com.caminando.Caminando.businesslayer.services.dto.user;

import com.caminando.Caminando.datalayer.entities.RoleEntity;
import lombok.Builder;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Data
public class RegisteredUserDTO {
    Long id;
    String firstName;
    String lastName;
    String username;
    String bio;
    String email;
    String city;
    @JsonIgnore
    String password;
    boolean enabled;
    Long follow;
    Long followers;
    private final List<RoleEntity> roles;

    @Builder(setterPrefix = "with")
    public RegisteredUserDTO(Long id, String firstName, String lastName, String username,String bio, String email,String city,String password,boolean enabled,Long follow,Long followers, List<RoleEntity> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.bio = bio;
        this.email = email;
        this.city = city;
        this.password = password;
        this.follow = follow;
        this.followers = followers;
        this.enabled = enabled;
        this.roles = roles;
    }
}
