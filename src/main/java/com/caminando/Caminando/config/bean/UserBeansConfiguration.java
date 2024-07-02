package com.caminando.Caminando.config.bean;
import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.TripResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.LoginResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisterUserDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.caminando.Caminando.datalayer.entities.travel.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
public class UserBeansConfiguration {

    @Bean
    @Scope("singleton")
    public Mapper<RegisterUserDTO, User> mapRegisterUser2UserEntity() {
        return input -> User.builder()
                .withFirstName(input.getFirstName())
                .withLastName(input.getLastName())
                .withUsername(input.getUsername())
                .withEmail(input.getEmail())
                .withPassword(input.getPassword())
                .build();
    }

    @Bean
    @Scope("singleton")
    public Mapper<User, RegisteredUserDTO> mapUserEntity2RegisteredUser() {
        return input -> RegisteredUserDTO.builder()
                .withId(input.getId())
                .withFirstName(input.getFirstName())
                .withLastName(input.getLastName())
                .withUsername(input.getUsername())
                .withCity(input.getCity())
                .withEmail(input.getEmail())
                .withRoles(input.getRoles())
                .withEnabled(input.isEnabled())
                .withImage(toImageDTO(input.getProfileImage()))
                .withTrips(Optional.ofNullable(input.getTrips())
                        .orElseGet(Collections::emptyList)
                        .stream()
                        .map(this::toTripDTO)
                        .collect(Collectors.toList()))
                .build();
    }


    @Bean
    @Scope("singleton")
    public Mapper<RegisteredUserDTO, User> mapRegisteredUser2UserEntity() {
        return input -> User.builder()
                .withFirstName(input.getFirstName())
                .withLastName(input.getLastName())
                .withUsername(input.getUsername())
                .withCity(input.getCity())
                .withEmail(input.getEmail())
                .withPassword(input.getPassword())
                .withBio(input.getBio())
                .withIsEnabled(input.isEnabled())
                .withRoles(input.getRoles())
                .withFollow(input.getFollow())
                .withFollowers(input.getFollowers())
                .withProfileImage(toImageEntity(input.getImage()))
                .withTrips(input.getTrips().stream()
                        .map(this::toTripEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    @Bean
    @Scope("singleton")
    public Mapper<User, LoginResponseDTO> mapUserEntity2LoginResponse() {
        return input -> LoginResponseDTO.builder()
                .withUser(RegisteredUserDTO.builder()
                        .withId(input.getId())
                        .withFirstName(input.getFirstName())
                        .withLastName(input.getLastName())
                        .withUsername(input.getUsername())
                        .withEmail(input.getEmail())
                        .withRoles(input.getRoles())
                        .withEnabled(input.isEnabled())
                        .build())
                .build();
    }

    private RegisteredUserDTO toRegisteredUserDTO(User user) {
        return RegisteredUserDTO.builder()
                .withId(user.getId())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withUsername(user.getUsername())
                .withCity(user.getCity())
                .withEmail(user.getEmail())
                .withRoles(user.getRoles())
                .withEnabled(user.isEnabled())
                .withImage(toImageDTO(user.getProfileImage()))
                .withTrips(user.getTrips().stream()
                        .map(this::toTripDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    private User toUserEntity(RegisteredUserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return User.builder()
                .withFirstName(userDTO.getFirstName())
                .withLastName(userDTO.getLastName())
                .withUsername(userDTO.getUsername())
                .withCity(userDTO.getCity())
                .withEmail(userDTO.getEmail())
                .withRoles(userDTO.getRoles())
                .withProfileImage(toImageEntity(userDTO.getImage()))
                .withTrips(userDTO.getTrips().stream()
                        .map(this::toTripEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    private Image toImageEntity(ImageResponseDTO imageResponseDTO) {
        if (imageResponseDTO == null) {
            return null;
        }
        Image image = new Image();
        image.setImageURL(imageResponseDTO.getImageURL());
        image.setDescription(imageResponseDTO.getDescription());
        return image;
    }

    private ImageResponseDTO toImageDTO(Image image) {
        if (image == null) {
            return null;
        }
        return ImageResponseDTO.builder()
                .withImageURL(image.getImageURL())
                .withDescription(image.getDescription())
                .build();
    }

    private TripResponseDTO toTripDTO(Trip trip) {
        if (trip == null) {
            return null;
        }
        return TripResponseDTO.builder()
                .withId(trip.getId())
                .withTitle(trip.getTitle())
                .withDescription(trip.getDescription())
                .withStartDate(trip.getStartDate())
                .withEndDate(trip.getEndDate())
                .withStatus(trip.getStatus())
                .withPrivacy(trip.getPrivacy())
                .withCoverImage(toImageDTO(trip.getCoverImage()))
                .build();
    }

    private Trip toTripEntity(TripResponseDTO tripDTO) {
        if (tripDTO == null) {
            return null;
        }
        return Trip.builder()

                .withTitle(tripDTO.getTitle())
                .withDescription(tripDTO.getDescription())
                .withStartDate(tripDTO.getStartDate())
                .withEndDate(tripDTO.getEndDate())
                .withStatus(tripDTO.getStatus())
                .withPrivacy(tripDTO.getPrivacy())
                .withCoverImage(toImageEntity(tripDTO.getCoverImage()))
                .build();
    }
}
