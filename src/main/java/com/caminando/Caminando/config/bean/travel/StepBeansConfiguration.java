package com.caminando.Caminando.config.bean.travel;

import com.caminando.Caminando.businesslayer.services.dto.ImageResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.*;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.travel.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Configuration
public class StepBeansConfiguration {

    @Bean
    @Primary
    @Scope("singleton")
    public Mapper<StepResponseDTO, StepRequestDTO> mapStepResponseToRequest() {
        return (input) -> StepRequestDTO.builder()
                .withDescription(input.getDescription())
                .withLikes(input.getLikes())
                .withArrivalDate(input.getArrivalDate())
                .withDepartureDate(input.getDepartureDate())
                .withTrip(input.getTrip())
                .withComments(input.getComments())
                .withImages(input.getImages())
                .withPosition(input.getPosition())
                .build();
    }

    @Bean
    @Scope("singleton")
    public Mapper<StepRequestDTO, StepResponseDTO> mapStepRequestToResponse() {
        return (input) -> StepResponseDTO.builder()
                .withDescription(input.getDescription())
                .withLikes(input.getLikes())
                .withArrivalDate(input.getArrivalDate())
                .withDepartureDate(input.getDepartureDate())
                .withTrip(input.getTrip())
                .withComments(input.getComments())
                .withImages(input.getImages())
                .withPosition(input.getPosition())
                .build();
    }
    @Bean
    @Scope("singleton")
    public Mapper<StepRequestDTO, Step> mapStepDTOToEntity() {
        return (input) -> {
            Step step = new Step();
            step.setDescription(input.getDescription());
            step.setLikes(input.getLikes());
            step.setArrivalDate(input.getArrivalDate());
            step.setDepartureDate(input.getDepartureDate());
            step.setTrip(toTripEntity(input.getTrip()));
            step.setComments(input.getComments() != null ? input.getComments().stream().map(this::toCommentEntity).collect(Collectors.toList()) : new ArrayList<>());
            step.setImages(input.getImages() != null ? input.getImages().stream().map(this::toImageEntity).collect(Collectors.toList()) : new ArrayList<>());
            step.setPosition(input.getPosition() != null ? toPositionEntity(input.getPosition()) : null);
            return step;
        };
    }

    @Bean
    @Scope("singleton")
    public Mapper<Step, StepRequestDTO> mapStepEntityToDTO() {
        return (input) -> StepRequestDTO.builder()
                .withDescription(input.getDescription())
                .withLikes(input.getLikes())
                .withArrivalDate(input.getArrivalDate())
                .withDepartureDate(input.getDepartureDate())
                .withTrip(toTripDTO(input.getTrip()))
                .withComments(input.getComments() != null ? input.getComments().stream().map(this::toCommentRequestDTO).collect(Collectors.toList()) : new ArrayList<>())
                .withImages(input.getImages() != null ? input.getImages().stream().map(this::toImageDTO).collect(Collectors.toList()) : new ArrayList<>())
                .withPosition(input.getPosition() != null ? toPositionDTO(input.getPosition()) : null)
                .build();
    }

    @Bean
    @Scope("singleton")
    public Mapper<Step, StepResponseDTO> mapStepEntityToResponse() {
        return (input) -> StepResponseDTO.builder()
                .withDescription(input.getDescription())
                .withLikes(input.getLikes())
                .withArrivalDate(input.getArrivalDate())
                .withDepartureDate(input.getDepartureDate())
                .withTrip(toTripDTO(input.getTrip()))
                .withComments(input.getComments() != null ? input.getComments().stream().map(this::toCommentResponseDTO).collect(Collectors.toList()) : new ArrayList<>())
                .withImages(input.getImages() != null ? input.getImages().stream().map(this::toImageDTO).collect(Collectors.toList()) : new ArrayList<>())
                .withPosition(input.getPosition() != null ? toPositionDTO(input.getPosition()) : null)
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
                .withSteps(trip.getSteps() != null ? trip.getSteps().stream().map(this::toStepResponseDTO).collect(Collectors.toList()) : null)
                .withCoverImage(trip.getCoverImage() != null ? toImageDTO(trip.getCoverImage()) : null)
                .build();
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

    private StepResponseDTO toStepResponseDTO(Step step) {
        return StepResponseDTO.builder()
                .withDescription(step.getDescription())
                .withLikes(step.getLikes())
                .withArrivalDate(step.getArrivalDate())
                .withDepartureDate(step.getDepartureDate())
                .withTrip(step.getTrip() != null ? toTripDTO(step.getTrip()) : null)
                .withComments(step.getComments().stream().map(this::toCommentResponseDTO).collect(Collectors.toList()))
                .withImages(step.getImages().stream().map(this::toImageDTO).collect(Collectors.toList()))
                .build();
    }

    private Step toStepEntity(StepResponseDTO stepResponseDTO) {
        Step step = new Step();
        step.setDescription(stepResponseDTO.getDescription());
        step.setLikes(stepResponseDTO.getLikes());
        step.setArrivalDate(stepResponseDTO.getArrivalDate());
        step.setDepartureDate(stepResponseDTO.getDepartureDate());
        step.setTrip(stepResponseDTO.getTrip() != null ? toTripEntity(stepResponseDTO.getTrip()) : null);
        step.setComments(stepResponseDTO.getComments().stream().map(this::toCommentEntity).collect(Collectors.toList()));
        step.setImages(stepResponseDTO.getImages().stream().map(this::toImageEntity).collect(Collectors.toList()));
        return step;
    }

    private Position toPositionEntity(PositionResponseDTO positionResponseDTO) {
        Position position = new Position();
        position.setLatitude(positionResponseDTO.getLatitude());
        position.setLongitude(positionResponseDTO.getLongitude());
        position.setNomeLocalita(positionResponseDTO.getNomeLocalita());
        position.setTimestamp(positionResponseDTO.getTimestamp());
        return position;
    }

    private PositionResponseDTO toPositionDTO(Position position) {
        return PositionResponseDTO.builder()
                .withLatitude(position.getLatitude())
                .withLongitude(position.getLongitude())
                .withNomeLocalita(position.getNomeLocalita())
                .withTimestamp(position.getTimestamp())
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

    private CommentResponseDTO toCommentResponseDTO(Comment comment) {
        return CommentResponseDTO.builder()
                .withText(comment.getText())
                .withDate(comment.getDate())
                .withStep(comment.getStep() != null ? toStepResponseDTO(comment.getStep()) : null)
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
