package com.caminando.Caminando.config.bean.travel;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.*;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.travel.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

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
                .withStep(toStepRequestDTO(toStepEntity(input.getStep()))) // Modificato qui
                .build();
    }


    @Bean
    @Scope("singleton")
    public Mapper<PositionRequestDTO, Position> mapPositionDTOToEntity() {
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
    public Mapper<Position, PositionResponseDTO> mapPositionEntityToResponse() {
        return (input) -> PositionResponseDTO.builder()
                .withLatitude(input.getLatitude())
                .withLongitude(input.getLongitude())
                .withTimestamp(input.getTimestamp())
                .withNomeLocalita(input.getNomeLocalita())
                .withStep(toStepRequestDTO(input.getStep())) // Modificato qui
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
                .withRoles(userDTO.getRoles())
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
    private Comment toCommentEntity(CommentResponseDTO commentRequestDTO) {
        Comment comment = new Comment();
        comment.setText(commentRequestDTO.getText());
        comment.setDate(commentRequestDTO.getDate());
        comment.setStep(commentRequestDTO.getStep() != null ? toStepEntity(commentRequestDTO.getStep()) : null);
        comment.setUser(commentRequestDTO.getUser() != null ? toUserEntity(commentRequestDTO.getUser()) : null);
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
}
