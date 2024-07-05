package com.caminando.Caminando.businesslayer.services.dto.user;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.TripResponseDTO;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.RoleEntity;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    List<RolesResponseDTO> roles;
    ImageResponseDTO image;
    List<TripResponseDTO> trips;

}
