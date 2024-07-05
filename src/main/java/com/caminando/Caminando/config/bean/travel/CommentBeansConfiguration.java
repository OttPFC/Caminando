package com.caminando.Caminando.config.bean.travel;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.*;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RolesResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.RoleEntity;
import com.caminando.Caminando.datalayer.entities.travel.Comment;
import com.caminando.Caminando.datalayer.entities.travel.Step;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.caminando.Caminando.datalayer.entities.travel.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.stream.Collectors;

@Configuration
public class CommentBeansConfiguration {
    @Bean
    @Scope("singleton")
    public Mapper<CommentRequestDTO, Comment> mapCommentDTOToEntity() {
        return (input) -> {
            Comment comment = new Comment();
            comment.setText(input.getText());
            comment.setDate(input.getDate());
            comment.setStep(toStepEntity(input.getStep()));
            comment.setUser(toUserEntity(input.getUser()));
            return comment;
        };
    }

    @Bean
    @Scope("singleton")
    public Mapper<Comment, CommentRequestDTO> mapCommentEntityToDTO() {
        return (input) -> CommentRequestDTO.builder()
                .withText(input.getText())
                .withDate(input.getDate())
                .withStep(toStepRequestDTO(input.getStep()))
                .withUser(toRegisteredUserDTO(input.getUser()))
                .build();
    }

    @Bean
    @Scope("singleton")
    public Mapper<Comment, CommentResponseDTO> mapCommentEntityToResponse() {
        return (input) -> CommentResponseDTO.builder()
                .withText(input.getText())
                .withDate(input.getDate())
                .withStep(toStepRequestDTO(input.getStep()))
                .withUser(toRegisteredUserDTO(input.getUser()))
                .build();
    }

    private CommentResponseDTO toCommentRequestDTO(Comment comment) {
        return CommentResponseDTO.builder()
                .withText(comment.getText())
                .withDate(comment.getDate())
                .withStep(comment.getStep() != null ? toStepRequestDTO(comment.getStep()) : null)
                .withUser(comment.getUser() != null ? toRegisteredUserDTO(comment.getUser()) : null)
                .build();
    }

    private CommentResponseDTO toCommentResponseDTO(Comment comment) {
        return CommentResponseDTO.builder()
                .withText(comment.getText())
                .withDate(comment.getDate())
                .withStep(comment.getStep() != null ? toStepRequestDTO(comment.getStep()) : null)
                .withUser(comment.getUser() != null ? toRegisteredUserDTO(comment.getUser()) : null)
                .build();
    }

    private Comment toCommentEntity(CommentResponseDTO commentRequestDTO) {
        Comment comment = new Comment();
        comment.setText(commentRequestDTO.getText());
        comment.setDate(commentRequestDTO.getDate());
        comment.setStep(commentRequestDTO.getStep() != null ? toStepEntity(commentRequestDTO.getStep()) : null);
        comment.setUser(commentRequestDTO.getUser() != null ? toUserEntity(commentRequestDTO.getUser()) : null);
        return comment;
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

    private Trip toTripEntity(TripResponseDTO tripRequestDTO) {
        Trip trip = new Trip();
        trip.setTitle(tripRequestDTO.getTitle());
        trip.setDescription(tripRequestDTO.getDescription());
        trip.setLikes(tripRequestDTO.getLikes());
        trip.setStartDate(tripRequestDTO.getStartDate());
        trip.setEndDate(tripRequestDTO.getEndDate());
        trip.setStatus(tripRequestDTO.getStatus());
        trip.setPrivacy(tripRequestDTO.getPrivacy());
        trip.setUser(tripRequestDTO.getUser() != null ? toUserEntity(tripRequestDTO.getUser()) : null);
        trip.setSteps(tripRequestDTO.getSteps() != null ? tripRequestDTO.getSteps().stream().map(this::toStepEntity).collect(Collectors.toList()) : null);
        trip.setCoverImage(tripRequestDTO.getCoverImage() != null ? toImageEntity(tripRequestDTO.getCoverImage()) : null);
        return trip;
    }

    private TripResponseDTO toTripDTO(Trip trip) {
        return TripResponseDTO.builder()
                .withTitle(trip.getTitle())
                .withDescription(trip.getDescription())
                .withStartDate(trip.getStartDate())
                .withEndDate(trip.getEndDate())
                .withStatus(trip.getStatus())
                .withPrivacy(trip.getPrivacy())
                .withUser(trip.getUser() != null ? toRegisteredUserDTO(trip.getUser()) : null)
                .withSteps(trip.getSteps() != null ? trip.getSteps().stream().map(this::toStepRequestDTO).collect(Collectors.toList()) : null)
                .withCoverImage(trip.getCoverImage() != null ? toImageDTO(trip.getCoverImage()) : null)
                .build();
    }
    private Step toStepEntity(StepResponseDTO stepRequestDTO) {
        Step step = new Step();
        step.setDescription(stepRequestDTO.getDescription());
        step.setLikes(stepRequestDTO.getLikes());
        step.setArrivalDate(stepRequestDTO.getArrivalDate());
        step.setDepartureDate(stepRequestDTO.getDepartureDate());
        step.setTrip(stepRequestDTO.getTrip() != null ? toTripEntity(stepRequestDTO.getTrip()) : null);
        step.setComments(stepRequestDTO.getComments().stream().map(this::toCommentEntity).collect(Collectors.toList()));
        step.setImages(stepRequestDTO.getImages().stream().map(this::toImageEntity).collect(Collectors.toList()));
        return step;
    }
    private StepResponseDTO toStepRequestDTO(Step step) {
        return StepResponseDTO.builder()
                .withDescription(step.getDescription())
                .withLikes(step.getLikes())
                .withArrivalDate(step.getArrivalDate())
                .withDepartureDate(step.getDepartureDate())
                .withTrip(step.getTrip() != null ? toTripDTO(step.getTrip()) : null)
                .withComments(step.getComments().stream().map(this::toCommentRequestDTO).collect(Collectors.toList()))
                .withImages(step.getImages().stream().map(this::toImageDTO).collect(Collectors.toList()))
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


