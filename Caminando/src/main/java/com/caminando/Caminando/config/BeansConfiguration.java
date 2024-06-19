package com.caminando.Caminando.config;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.*;
import com.caminando.Caminando.businesslayer.services.dto.user.*;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.travel.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

@Configuration
public class BeansConfiguration {

	@Bean
	public Pageable defaultPageable() {
		int page = 0; // Numero di pagina predefinito (prima pagina)
		int size = 10; // Dimensione della pagina predefinita
		return PageRequest.of(page, size);
	}

	@Bean
	@Scope("singleton")
	public Mapper<RegisterUserDTO, User> mapRegisterUser2UserEntity() {
		return (input) -> User.builder()
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
		return (input) -> RegisteredUserDTO.builder()
				.withId(input.getId())
				.withFirstName(input.getFirstName())
				.withLastName(input.getLastName())
				.withUsername(input.getUsername())
				.withEmail(input.getEmail())
				.withRoles(input.getRoles())
				.build();
	}

	@Bean
	@Scope("singleton")
	public Mapper<User, LoginResponseDTO> mapUserEntity2LoginResponse() {
		return (input) -> LoginResponseDTO.builder()
				.withId(input.getId())
				.withFirstName(input.getFirstName())
				.withLastName(input.getLastName())
				.withUsername(input.getUsername())
				.withEmail(input.getEmail())
				.withRoles(input.getRoles())
				.build();
	}

	@Bean
	@Scope("singleton")
	public Mapper<PositionDTO, Position> mapPositionDTOToEntity() {
		return (input) -> {
			Position position = new Position();
			position.setLatitude(input.getLatitude());
			position.setLongitude(input.getLongitude());
			position.setTimestamp(input.getTimestamp());
			position.setNomeLocalita(input.getNomeLocalita());
			return position;
		};
	}

	@Bean
	@Scope("singleton")
	public Mapper<Position, PositionDTO> mapPositionEntityToDTO() {
		return (input) -> {
			PositionDTO positionDTO = new PositionDTO();
			positionDTO.setLatitude(input.getLatitude());
			positionDTO.setLongitude(input.getLongitude());
			positionDTO.setTimestamp(input.getTimestamp());
			positionDTO.setNomeLocalita(input.getNomeLocalita());
			return positionDTO;
		};
	}

	@Bean
	@Scope("singleton")
	public Mapper<TripDTO, Trip> mapTripDTOToEntity() {
		return (input) -> {
			Trip trip = new Trip();
			trip.setTitle(input.getTitle());
			trip.setDescription(input.getDescription());
			trip.setLikes(input.getLikes());
			trip.setStartDate(input.getStartDate());
			trip.setEndDate(input.getEndDate());
			trip.setStatus(input.getStatus());
			trip.setPrivacy(input.getPrivacy());
			trip.setUser(toUserEntity(input.getUser()));
			trip.setSteps(input.getSteps().stream().map(this::toStepEntity).collect(Collectors.toList()));
			trip.setCoverImage(toImageEntity(input.getCoverImage()));
			return trip;
		};
	}

	@Bean
	@Scope("singleton")
	public Mapper<Trip, TripDTO> mapTripEntityToDTO() {
		return (input) -> TripDTO.builder()
				.withTitle(input.getTitle())
				.withDescription(input.getDescription())
				.withLikes(input.getLikes())
				.withStartDate(input.getStartDate())
				.withEndDate(input.getEndDate())
				.withStatus(input.getStatus())
				.withPrivacy(input.getPrivacy())
				.withUser(toRegisteredUserDTO(input.getUser()))
				.withSteps(input.getSteps().stream().map(this::toStepDTO).collect(Collectors.toList()))
				.withCoverImage(toImageDTO(input.getCoverImage()))
				.build();
	}

	@Bean
	@Scope("singleton")
	public Mapper<CommentDTO, Comment> mapCommentDTOToEntity() {
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
	public Mapper<Comment, CommentDTO> mapCommentEntityToDTO() {
		return (input) -> CommentDTO.builder()
				.withText(input.getText())
				.withDate(input.getDate())
				.withStep(toStepDTO(input.getStep()))
				.withUser(toRegisteredUserDTO(input.getUser()))
				.build();
	}

	@Bean
	@Scope("singleton")
	public Mapper<StepDTO, Step> mapStepDTOToEntity() {
		return (input) -> {
			Step step = new Step();
			step.setDescription(input.getDescription());
			step.setLikes(input.getLikes());
			step.setArrivalDate(input.getArrivalDate());
			step.setDepartureDate(input.getDepartureDate());
//			step.setTrip(toTripEntity(input.getTrip()));
			step.setComments(input.getComments().stream().map(this::toCommentEntity).collect(Collectors.toList()));
			step.setImages(input.getImages().stream().map(this::toImageEntity).collect(Collectors.toList()));
			return step;
		};
	}

	@Bean
	@Scope("singleton")
	public Mapper<Step, StepDTO> mapStepEntityToDTO() {
		return (input) -> StepDTO.builder()
				.withDescription(input.getDescription())
				.withLikes(input.getLikes())
				.withArrivalDate(input.getArrivalDate())
				.withDepartureDate(input.getDepartureDate())
//				.withTrip(toTripDTO(input.getTrip()))
				.withComments(input.getComments().stream().map(this::toCommentDTO).collect(Collectors.toList()))
				.withImages(input.getImages().stream().map(this::toImageDTO).collect(Collectors.toList()))
				.build();
	}

	private RegisteredUserDTO toRegisteredUserDTO(User user) {
		return RegisteredUserDTO.builder()
				.withId(user.getId())
				.withFirstName(user.getFirstName())
				.withLastName(user.getLastName())
				.withUsername(user.getUsername())
				.withEmail(user.getEmail())
				.withRoles(user.getRoles())
				.build();
	}

	private User toUserEntity(RegisteredUserDTO userDTO) {
		return User.builder()
				.withFirstName(userDTO.getFirstName())
				.withLastName(userDTO.getLastName())
				.withUsername(userDTO.getUsername())
				.withEmail(userDTO.getEmail())
				.withRoles(userDTO.getRoles())
				.build();
	}

	private StepDTO toStepDTO(Step step) {
		return StepDTO.builder()
				.withDescription(step.getDescription())
				.withLikes(step.getLikes())
				.withArrivalDate(step.getArrivalDate())
				.withDepartureDate(step.getDepartureDate())
//				.withTrip(step.getTrip() != null ? toTripDTO(step.getTrip()) : null)
				.withComments(step.getComments().stream().map(this::toCommentDTO).collect(Collectors.toList()))
				.withImages(step.getImages().stream().map(this::toImageDTO).collect(Collectors.toList()))
				.build();
	}

	private Step toStepEntity(StepDTO stepDTO) {
		Step step = new Step();
		step.setDescription(stepDTO.getDescription());
		step.setLikes(stepDTO.getLikes());
		step.setArrivalDate(stepDTO.getArrivalDate());
		step.setDepartureDate(stepDTO.getDepartureDate());
//		step.setTrip(stepDTO.getTrip() != null ? toTripEntity(stepDTO.getTrip()) : null);
		step.setComments(stepDTO.getComments().stream().map(this::toCommentEntity).collect(Collectors.toList()));
		step.setImages(stepDTO.getImages().stream().map(this::toImageEntity).collect(Collectors.toList()));
		return step;
	}

	private CommentDTO toCommentDTO(Comment comment) {
		return CommentDTO.builder()
				.withText(comment.getText())
				.withDate(comment.getDate())
				.withStep(comment.getStep() != null ? toStepDTO(comment.getStep()) : null)
				.withUser(comment.getUser() != null ? toRegisteredUserDTO(comment.getUser()) : null)
				.build();
	}

	private Comment toCommentEntity(CommentDTO commentDTO) {
		Comment comment = new Comment();
		comment.setText(commentDTO.getText());
		comment.setDate(commentDTO.getDate());
		comment.setStep(commentDTO.getStep() != null ? toStepEntity(commentDTO.getStep()) : null);
		comment.setUser(commentDTO.getUser() != null ? toUserEntity(commentDTO.getUser()) : null);
		return comment;
	}

	private ImageDTO toImageDTO(Image image) {
		return ImageDTO.builder()
				.withImageURL(image.getImageURL())
				.withDescription(image.getDescription())
				.build();
	}

	private Image toImageEntity(ImageDTO imageDTO) {
		Image image = new Image();
		image.setImageURL(imageDTO.getImageURL());
		image.setDescription(imageDTO.getDescription());
		return image;
	}

	private TripDTO toTripDTO(Trip trip) {
		return TripDTO.builder()

				.withTitle(trip.getTitle())
				.withDescription(trip.getDescription())
				.withLikes(trip.getLikes())
				.withStartDate(trip.getStartDate())
				.withEndDate(trip.getEndDate())
				.withStatus(trip.getStatus())
				.withPrivacy(trip.getPrivacy())
				.withUser(trip.getUser() != null ? toRegisteredUserDTO(trip.getUser()) : null)
				.withSteps(trip.getSteps() != null ? trip.getSteps().stream().map(this::toStepDTO).collect(Collectors.toList()) : null)
				.withCoverImage(trip.getCoverImage() != null ? toImageDTO(trip.getCoverImage()) : null)
				.build();
	}

	private Trip toTripEntity(TripDTO tripDTO) {
		Trip trip = new Trip();

		trip.setTitle(tripDTO.getTitle());
		trip.setDescription(tripDTO.getDescription());
		trip.setLikes(tripDTO.getLikes());
		trip.setStartDate(tripDTO.getStartDate());
		trip.setEndDate(tripDTO.getEndDate());
		trip.setStatus(tripDTO.getStatus());
		trip.setPrivacy(tripDTO.getPrivacy());
		trip.setUser(tripDTO.getUser() != null ? toUserEntity(tripDTO.getUser()) : null);
		trip.setSteps(tripDTO.getSteps() != null ? tripDTO.getSteps().stream().map(this::toStepEntity).collect(Collectors.toList()) : null);
		trip.setCoverImage(tripDTO.getCoverImage() != null ? toImageEntity(tripDTO.getCoverImage()) : null);
		return trip;
	}
}
