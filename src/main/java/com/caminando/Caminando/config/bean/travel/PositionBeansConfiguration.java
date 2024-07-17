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

import java.util.ArrayList;
import java.util.stream.Collectors;

@Configuration
public class PositionBeansConfiguration {

    @Bean
    @Scope("singleton")
    public Mapper<PositionRequestDTO, PositionResponseDTO> mapRequestToResponse() {
        return (input) -> PositionResponseDTO.builder()
                .withLatitude(input.getLatitude())
                .withLongitude(input.getLongitude())
                .withTimestamp(input.getTimestamp())
                .withNomeLocalita(input.getNomeLocalita())
                .withUser(input.getUser())
                .withStep(toStepRequestDTO(toStepEntity(input.getStep())))
                .build();
    }
    @Bean
    @Scope("singleton")
    public Mapper<PositionResponseDTO, Position> mapPositionDTOToEntity() {
        return (input) -> {
            Position position = new Position();
            position.setLatitude(input.getLatitude());
            position.setLongitude(input.getLongitude());
            position.setTimestamp(input.getTimestamp());
            position.setNomeLocalita(input.getNomeLocalita());
            position.setStep(input.getStep() != null ? toStepEntity(input.getStep()) : null);
            return position;
        };
    }
    @Bean
    @Scope("singleton")
    public Mapper<Position, PositionRequestDTO> mapPositionEntityToDTO() {
        return (input) -> PositionRequestDTO.builder()
                .withLatitude(input.getLatitude())
                .withLongitude(input.getLongitude())
                .withTimestamp(input.getTimestamp())
                .withNomeLocalita(input.getNomeLocalita())
                .withStep(toStepRequestDTO(input.getStep()))
                .build();
    }

    @Bean
    @Scope("singleton")
    public Mapper<PositionRequestDTO, Position> positionRequestDTOToEntity() {
        return (input) -> Position.builder()
                .withLatitude(input.getLatitude())
                .withLongitude(input.getLongitude())
                .withTimestamp(input.getTimestamp())
                .withNomeLocalita(input.getNomeLocalita())
                .withStep(toStepEntity(input.getStep()))
                .build();
    }

    @Bean
    @Scope("singleton")
    public Mapper<Position, PositionResponseDTO> mapPositionEntityToResponse() {
        return (input) -> PositionResponseDTO.builder()
                .withId(input.getId())
                .withId(input.getId())
                .withLatitude(input.getLatitude())
                .withLongitude(input.getLongitude())
                .withTimestamp(input.getTimestamp())
                .withNomeLocalita(input.getNomeLocalita())
                .withStep(toStepRequestDTO(input.getStep()))
                .build();
    }


    private Position toPositionEntity(PositionRequestDTO positionRequestDTO) {
        Position position = new Position();
        position.setLatitude(positionRequestDTO.getLatitude());
        position.setLongitude(positionRequestDTO.getLongitude());
        position.setTimestamp(positionRequestDTO.getTimestamp());
        position.setNomeLocalita(positionRequestDTO.getNomeLocalita());
        position.setStep(positionRequestDTO.getStep() != null ? toStepEntity(positionRequestDTO.getStep()) : null);
        return position;
    }

    private PositionRequestDTO toPositionDTO(Position position) {
        return PositionRequestDTO.builder()
                .withLatitude(position.getLatitude())
                .withLongitude(position.getLongitude())
                .withTimestamp(position.getTimestamp())
                .withNomeLocalita(position.getNomeLocalita())
                .withStep(position.getStep() != null ? toStepRequestDTO(position.getStep()) : null)
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
    private Trip toTripEntity(TripResponseDTO tripResponseDTO) {
        Trip trip = new Trip();
        trip.setTitle(tripResponseDTO.getTitle());
        trip.setDescription(tripResponseDTO.getDescription());
        trip.setStartDate(tripResponseDTO.getStartDate());
        trip.setEndDate(tripResponseDTO.getEndDate());
        trip.setStatus(tripResponseDTO.getStatus());
        trip.setPrivacy(tripResponseDTO.getPrivacy());
        trip.setUser(tripResponseDTO.getUser() != null ? toUserEntity(tripResponseDTO.getUser()) : null);
        trip.setSteps(tripResponseDTO.getSteps() != null ? tripResponseDTO.getSteps().stream().map(this::toStepEntity).collect(Collectors.toList()) : null);
        trip.setCoverImage(tripResponseDTO.getCoverImage() != null ? toImageEntity(tripResponseDTO.getCoverImage()) : null);
        return trip;
    }
    private Step toStepEntity(StepResponseDTO stepResponseDTO) {
        if (stepResponseDTO == null) {
            return null;
        }

        Step step = new Step();
        step.setDescription(stepResponseDTO.getDescription());
        step.setLikes(stepResponseDTO.getLikes());
        step.setArrivalDate(stepResponseDTO.getArrivalDate());
        step.setDepartureDate(stepResponseDTO.getDepartureDate());
        step.setTrip(stepResponseDTO.getTrip() != null ? toTripEntity(stepResponseDTO.getTrip()) : null);

        // Handle null comments and images
        step.setComments(stepResponseDTO.getComments() != null
                ? stepResponseDTO.getComments().stream().map(this::toCommentEntity).collect(Collectors.toList())
                : new ArrayList<>());

        step.setImages(stepResponseDTO.getImages() != null
                ? stepResponseDTO.getImages().stream().map(this::toImageEntity).collect(Collectors.toList())
                : new ArrayList<>());

        return step;
    }

    private StepResponseDTO toStepRequestDTO(Step step) {
        if (step == null) {
            return null;
        }

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
    private Comment toCommentEntity(CommentResponseDTO commentResponseDTO) {
        Comment comment = new Comment();
        comment.setText(commentResponseDTO.getText());
        comment.setDate(commentResponseDTO.getDate());
        comment.setStep(commentResponseDTO.getStep() != null ? toStepEntity(commentResponseDTO.getStep()) : null);
        comment.setUser(commentResponseDTO.getUser() != null ? toUserEntity(commentResponseDTO.getUser()) : null);
        return comment;
    }
    private CommentResponseDTO toCommentRequestDTO(Comment comment) {
        return CommentResponseDTO.builder()
                .withText(comment.getText())
                .withDate(comment.getDate())
                .withStep(comment.getStep() != null ? toStepRequestDTO(comment.getStep()) : null)
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
