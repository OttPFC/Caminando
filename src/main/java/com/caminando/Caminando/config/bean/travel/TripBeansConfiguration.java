package com.caminando.Caminando.config.bean.travel;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.*;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RolesResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.RoleEntity;
import com.caminando.Caminando.datalayer.entities.travel.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class TripBeansConfiguration {

    @Bean
    @Scope("singleton")
    public Mapper<TripRequestDTO, Trip> mapTripRequestDTOToTrip() {
        return input -> {
            Trip trip = new Trip();
            trip.setTitle(input.getTitle());
            trip.setDescription(input.getDescription());
            trip.setStartDate(input.getStartDate());
            trip.setEndDate(input.getEndDate());
            trip.setStatus(input.getStatus());
            trip.setPrivacy(input.getPrivacy());
            trip.setUser(toUserEntity(input.getUser()));
            trip.setSteps(input.getSteps() != null ? input.getSteps().stream().map(this::toStepEntity).collect(Collectors.toList()) : Collections.emptyList());
            trip.setCoverImage(input.getCoverImage() != null ? toImageEntity(input.getCoverImage()) : null);
            return trip;
        };
    }

    @Bean
    @Scope("singleton")
    public Mapper<Trip, TripResponseDTO> mapTripToTripResponseDTO() {
        return input -> TripResponseDTO.builder()
                .withId(input.getId())
                .withTitle(input.getTitle())
                .withDescription(input.getDescription())
                .withLikes(input.getLikes())
                .withStartDate(input.getStartDate())
                .withEndDate(input.getEndDate())
                .withStatus(input.getStatus())
                .withPrivacy(input.getPrivacy())
                //.withUser(toRegisteredUserDTO(input.getUser()))
                .withSteps(input.getSteps() != null ? input.getSteps().stream().map(this::toStepResponseDTO).collect(Collectors.toList()) : Collections.emptyList())
                .withCoverImage(input.getCoverImage() != null ? toImageDTO(input.getCoverImage()) : null)
                .build();
    }

    private TripResponseDTO toTripResponseDTO(Trip trip) {
        return TripResponseDTO.builder()
                .withId(trip.getId())
                .withTitle(trip.getTitle())
                .withDescription(trip.getDescription())
                .withLikes(trip.getLikes())
                .withStartDate(trip.getStartDate())
                .withEndDate(trip.getEndDate())
                .withStatus(trip.getStatus())
                .withPrivacy(trip.getPrivacy())
                .withUser(toRegisteredUserDTO(trip.getUser()))
                .withSteps(trip.getSteps() != null ? trip.getSteps().stream().map(this::toStepResponseDTO).collect(Collectors.toList()) : Collections.emptyList())
                .withCoverImage(trip.getCoverImage() != null ? toImageDTO(trip.getCoverImage()) : null)
                .build();
    }

    private Trip toTripEntity(TripResponseDTO tripRequestDTO) {
        Trip trip = new Trip();
        trip.setTitle(tripRequestDTO.getTitle());
        trip.setDescription(tripRequestDTO.getDescription());
        trip.setStartDate(tripRequestDTO.getStartDate());
        trip.setEndDate(tripRequestDTO.getEndDate());
        trip.setStatus(tripRequestDTO.getStatus());
        trip.setPrivacy(tripRequestDTO.getPrivacy());
        trip.setUser(tripRequestDTO.getUser() != null ? toUserEntity(tripRequestDTO.getUser()) : null);
        trip.setSteps(tripRequestDTO.getSteps() != null ? tripRequestDTO.getSteps().stream().map(this::toStepEntity).collect(Collectors.toList()) : null);
        trip.setCoverImage(tripRequestDTO.getCoverImage() != null ? toImageEntity(tripRequestDTO.getCoverImage()) : null);
        return trip;
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
                .withRoles(userDTO.getRoles().stream().map(this::toRoleEntity).collect(Collectors.toList()))
                .build();
    }

    private RegisteredUserDTO toRegisteredUserDTO(User user) {
        if (user == null) {
            return null;
        }
        return RegisteredUserDTO.builder()
                .withId(user.getId())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withUsername(user.getUsername())
                .withCity(user.getCity())
                .withEmail(user.getEmail())
                .withRoles(user.getRoles().stream().map(this::toRoleDTO).collect(Collectors.toList()))
                .build();
    }

    private Step toStepEntity(StepResponseDTO stepResponseDTO) {
        Step step = new Step();

        step.setDescription(stepResponseDTO.getDescription());
        step.setLikes(stepResponseDTO.getLikes());
        step.setArrivalDate(stepResponseDTO.getArrivalDate());
        step.setDepartureDate(stepResponseDTO.getDepartureDate());
        // Evita la conversione ricorsiva
        // step.setTrip(stepResponseDTO.getTrip() != null ? toTripEntity(stepResponseDTO.getTrip()) : null);
        step.setComments(stepResponseDTO.getComments().stream().map(this::toCommentEntity).collect(Collectors.toList()));
        step.setImages(stepResponseDTO.getImages().stream().map(this::toImageEntity).collect(Collectors.toList()));
        return step;
    }

    private StepResponseDTO toStepResponseDTO(Step step) {
        return StepResponseDTO.builder()
                .withId(step.getId())
                .withDescription(step.getDescription())
                .withLikes(step.getLikes())
                .withArrivalDate(step.getArrivalDate())
                .withDepartureDate(step.getDepartureDate())
                // Evita la conversione ricorsiva
                // .withTrip(step.getTrip() != null ? toTripResponseDTO(step.getTrip()) : null)
                .withComments(step.getComments() != null ? step.getComments().stream().map(this::toCommentResponseDTO).collect(Collectors.toList()) : Collections.emptyList())
                .withImages(step.getImages() != null ? step.getImages().stream().map(this::toImageDTO).collect(Collectors.toList()) : Collections.emptyList())
                .withPosition(toPositionResponseDTO(step.getPosition()))
                .build();
    }


    private PositionResponseDTO toPositionResponseDTO(Position position) {
        if (position == null) {
            return null;
        }
        return PositionResponseDTO.builder()
                .withLatitude(position.getLatitude())
                .withLongitude(position.getLongitude())
                .withTimestamp(position.getTimestamp())
                .withNomeLocalita(position.getNomeLocalita())
                .build();
    }

    private Comment toCommentEntity(CommentResponseDTO commentRequestDTO) {
        Comment comment = new Comment();
        comment.setText(commentRequestDTO.getText());
        comment.setDate(commentRequestDTO.getDate());
        // Evita la conversione ricorsiva
        // comment.setStep(commentRequestDTO.getStep() != null ? toStepEntity(commentRequestDTO.getStep()) : null);
        comment.setUser(commentRequestDTO.getUser() != null ? toUserEntity(commentRequestDTO.getUser()) : null);
        return comment;
    }

    private CommentResponseDTO toCommentResponseDTO(Comment comment) {
        return CommentResponseDTO.builder()
                .withText(comment.getText())
                .withDate(comment.getDate())
                // Evita la conversione ricorsiva
                // .withStep(comment.getStep() != null ? toStepResponseDTO(comment.getStep()) : null)
                .withUser(comment.getUser() != null ? toRegisteredUserDTO(comment.getUser()) : null)
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



    private RolesResponseDTO toRoleDTO(RoleEntity roleEntity) {
        if (roleEntity == null) {
            return null;
        }
        return RolesResponseDTO.builder()
                .withRoleType(roleEntity.getRoleType())
                .build();
    }

    private RoleEntity toRoleEntity(RolesResponseDTO roleDTO) {
        if (roleDTO == null) {
            return null;
        }
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleType(roleDTO.getRoleType());
        return roleEntity;
    }
}
