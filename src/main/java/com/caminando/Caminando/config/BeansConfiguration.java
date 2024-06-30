package com.caminando.Caminando.config;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.*;
import com.caminando.Caminando.businesslayer.services.dto.travel.*;
import com.caminando.Caminando.businesslayer.services.dto.user.*;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.RoleEntity;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.*;
import com.caminando.Caminando.datalayer.entities.travel.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
public class BeansConfiguration {

	@Bean
	public Pageable defaultPageable() {
		return PageRequest.of(0, 10);
	}

	// User mapper
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
				.withCity(input.getCity())
				.withEmail(input.getEmail())
				.withRoles(input.getRoles())
				.withEnabled(input.isEnabled())
				.withImage(input.getProfileImage())
				.withTrips(input.getTrips())
				.build();
	}

	@Bean
	@Scope("singleton")
	public Mapper<RegisteredUserDTO, User> mapRegisteredUser2UserEntity() {
		return (input) -> User.builder()
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
				.withProfileImage(input.getImage())
				.withTrips(input.getTrips())
				.build();
	}

	@Bean
	@Scope("singleton")
	public Mapper<User, LoginResponseDTO> mapUserEntity2LoginResponse() {
		return (input) -> LoginResponseDTO.builder()
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

	// Position mapper
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
				.withStep(toStepRequestDTO(input.getStep()))
				.build();
	}

	// Trip Mapper
	@Bean
	@Scope("singleton")
	public Mapper<TripRequestDTO, Trip> mapTripDTOToEntity() {
		return (input) -> {
			Trip trip = new Trip();
			trip.setId(input.getId());
			trip.setTitle(input.getTitle());
			trip.setDescription(input.getDescription());
			trip.setLikes(input.getLikes());
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
	public Mapper<Trip, TripRequestDTO> mapTripEntityToDTO() {
		return (input) -> TripRequestDTO.builder()
				.withId(input.getId())
				.withTitle(input.getTitle())
				.withDescription(input.getDescription())
				.withLikes(input.getLikes())
				.withStartDate(input.getStartDate())
				.withEndDate(input.getEndDate())
				.withStatus(input.getStatus())
				.withPrivacy(input.getPrivacy())
				.withUser(toRegisteredUserDTO(input.getUser()))
				.withSteps(input.getSteps().stream().map(this::toStepRequestDTO).collect(Collectors.toList()))
				.withCoverImage(toImageDTO(input.getCoverImage()))
				.build();
	}


	@Bean
	@Scope("singleton")
	public Mapper<Trip, TripResponseDTO> tripToResponseDTO() {
		return (input) -> TripResponseDTO.builder()
				.withId(input.getId()) // Aggiungi l'ID qui
				.withTitle(input.getTitle())
				.withDescription(input.getDescription())
				.withLikes(input.getLikes())
				.withStartDate(input.getStartDate())
				.withEndDate(input.getEndDate())
				.withStatus(input.getStatus())
				.withPrivacy(input.getPrivacy())
				.withUser(toRegisteredUserDTO(input.getUser()))
				.withSteps(input.getSteps() != null ? input.getSteps().stream().map(this::toStepRequestDTO).collect(Collectors.toList()) : Collections.emptyList())
				.withCoverImage(input.getCoverImage() != null ? toImageDTO(input.getCoverImage()) : null)
				.build();
	}




	// Comment Mapper
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

	// Step Mapper
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
				.withComments(input.getComments() != null ? input.getComments().stream().map(this::toCommentRequestDTO).collect(Collectors.toList()) : new ArrayList<>())
				.withImages(input.getImages() != null ? input.getImages().stream().map(this::toImageDTO).collect(Collectors.toList()) : new ArrayList<>())
				.build();
	}


	// Itinerary Mapper
	@Bean
	@Scope("singleton")
	public Mapper<SuggestItineraryDTO, SuggestItinerary> mapSuggestItineraryDTOToEntity() {
		return (input) -> {
			SuggestItinerary suggestItinerary = new SuggestItinerary();
			suggestItinerary.setName(input.getName());
			suggestItinerary.setDescription(input.getDescription());
			suggestItinerary.setLocation(input.getLocation());
			suggestItinerary.setLikes(input.getLikes());
			suggestItinerary.setSuggest(Collections.singletonList(input.getSuggest()));
			suggestItinerary.setUser(toUserEntity(input.getUser()));
			suggestItinerary.setImage(toImageEntity(input.getImage()));
			suggestItinerary.setToDo(input.getToDo().stream().map(this::toToDoEntity).collect(Collectors.toList()));
			suggestItinerary.setRestaurant(input.getRestaurant().stream().map(this::toRestaurantEntity).collect(Collectors.toList()));
			suggestItinerary.setQuickFacts(input.getQuickFacts().stream().map(this::toQuickFactsEntity).collect(Collectors.toList()));
			suggestItinerary.setPlaceToStay(input.getPlaceToStay().stream().map(this::toPlaceToStayEntity).collect(Collectors.toList()));
			suggestItinerary.setFood(input.getFood().stream().map(this::toFoodEntity).collect(Collectors.toList()));
			return suggestItinerary;
		};
	}

	@Bean
	@Scope("singleton")
	public Mapper<SuggestItinerary, SuggestItineraryDTO> mapSuggestItineraryEntityToDTO() {
		return (input) -> SuggestItineraryDTO.builder()
				.withName(input.getName())
				.withDescription(input.getDescription())
				.withLocation(input.getLocation())
				.withLikes(input.getLikes())
				.withSuggest(String.valueOf(input.getSuggest()))
				.withUser(toRegisteredUserDTO(input.getUser()))
				.withImage(toImageDTO(input.getImage()))
				.withToDo(input.getToDo().stream().map(this::toToDoDTO).collect(Collectors.toList()))
				.withRestaurant(input.getRestaurant().stream().map(this::toRestaurantDTO).collect(Collectors.toList()))
				.withQuickFacts(input.getQuickFacts().stream().map(this::toQuickFactsDTO).collect(Collectors.toList()))
				.withPlaceToStay(input.getPlaceToStay().stream().map(this::toPlaceToStayDTO).collect(Collectors.toList()))
				.withFood(input.getFood().stream().map(this::toFoodDTO).collect(Collectors.toList()))
				.build();
	}

	@Bean
	@Scope("singleton")
	public Mapper<SuggestItinerary, SuggestItineraryResponseDTO> mapSuggestItineraryEntityToResponse() {
		return (input) -> SuggestItineraryResponseDTO.builder()
				.withName(input.getName())
				.withDescription(input.getDescription())
				.withLocation(input.getLocation())
				.withLikes(input.getLikes())
				.withSuggest(String.valueOf(input.getSuggest()))
				.withUser(toRegisteredUserDTO(input.getUser()))
				.withImage(toImageDTO(input.getImage()))
				.withToDo(input.getToDo().stream().map(this::toToDoDTO).collect(Collectors.toList()))
				.withRestaurant(input.getRestaurant().stream().map(this::toRestaurantDTO).collect(Collectors.toList()))
				.withQuickFacts(input.getQuickFacts().stream().map(this::toQuickFactsDTO).collect(Collectors.toList()))
				.withPlaceToStay(input.getPlaceToStay().stream().map(this::toPlaceToStayDTO).collect(Collectors.toList()))
				.withFood(input.getFood().stream().map(this::toFoodDTO).collect(Collectors.toList()))
				.build();
	}

	@Bean
	@Scope("singleton")
	public Mapper<ToDoDTO, ToDo> mapToDoDTOToEntity() {
		return (input) -> {
			ToDo toDo = new ToDo();
			toDo.setDescription(input.getDescription());
			toDo.setTitle(input.getTitle());
			toDo.setImage(toImageEntity(input.getImage()));
			toDo.setSuggestItinerary(toSuggestItineraryEntity(input.getSuggestItinerary()));
			return toDo;
		};
	}

	@Bean
	@Scope("singleton")
	public Mapper<ToDo, ToDoDTO> mapToDoEntityToDTO() {
		return (input) -> ToDoDTO.builder()
				.withDescription(input.getDescription())
				.withTitle(input.getTitle())
				.withImage(toImageDTO(input.getImage()))
				.withSuggestItinerary(toSuggestItineraryDTO(input.getSuggestItinerary()))
				.build();
	}
	@Bean
	@Scope("singleton")
	public Mapper<ToDo, ToDoResponseDTO> mapToDoEntityToResponse() {
		return (input) -> ToDoResponseDTO.builder()
				.withDescription(input.getDescription())
				.withTitle(input.getTitle())
				.withImage(toImageDTO(input.getImage()))
				.withSuggestItinerary(toSuggestItineraryDTO(input.getSuggestItinerary()))
				.build();
	}

	@Bean
	@Scope("singleton")
	public Mapper<RestaurantDTO, Restaurant> mapRestaurantDTOToEntity() {
		return (input) -> {
			Restaurant restaurant = new Restaurant();
			restaurant.setDescription(input.getDescription());
			restaurant.setTitle(input.getTitle());
			restaurant.setImage(toImageEntity(input.getImage()));
			restaurant.setSuggestItinerary(toSuggestItineraryEntity(input.getSuggestItinerary()));
			return restaurant;
		};
	}

	@Bean
	@Scope("singleton")
	public Mapper<Restaurant, RestaurantDTO> mapRestaurantEntityToDTO() {
		return (input) -> RestaurantDTO.builder()
				.withDescription(input.getDescription())
				.withTitle(input.getTitle())
				.withImage(toImageDTO(input.getImage()))
				.withSuggestItinerary(toSuggestItineraryDTO(input.getSuggestItinerary()))
				.build();
	}
	@Bean
	@Scope("singleton")
	public Mapper<Restaurant, RestaurantResponseDTO> mapRestaurantEntityToResponse() {
		return (input) -> RestaurantResponseDTO.builder()
				.withDescription(input.getDescription())
				.withTitle(input.getTitle())
				.withImage(toImageDTO(input.getImage()))
				.withSuggestItinerary(toSuggestItineraryDTO(input.getSuggestItinerary()))
				.build();
	}

	@Bean
	@Scope("singleton")
	public Mapper<QuickFactsDTO, QuickFacts> mapQuickFactsDTOToEntity() {
		return (input) -> {
			QuickFacts q = new QuickFacts();
			q.setDescription(input.getDescription());
			q.setTitle(input.getTitle());
			q.setImage(toImageEntity(input.getImage()));
			q.setSuggestItinerary(toSuggestItineraryEntity(input.getSuggestItinerary()));
			return q;
		};
	}

	@Bean
	@Scope("singleton")
	public Mapper<QuickFacts, QuickFactsDTO> mapQuickFactsEntityToDTO() {
		return (input) -> QuickFactsDTO.builder()
				.withDescription(input.getDescription())
				.withTitle(input.getTitle())
				.withImage(toImageDTO(input.getImage()))
				.withSuggestItinerary(toSuggestItineraryDTO(input.getSuggestItinerary()))
				.build();
	}
	@Bean
	@Scope("singleton")
	public Mapper<QuickFacts, QuickFactsResponseDTO> mapQuickFactsEntityToResponse() {
		return (input) -> QuickFactsResponseDTO.builder()
				.withDescription(input.getDescription())
				.withTitle(input.getTitle())
				.withImage(toImageDTO(input.getImage()))
				.withSuggestItinerary(toSuggestItineraryDTO(input.getSuggestItinerary()))
				.build();
	}
	@Bean
	@Scope("singleton")
	public Mapper<PlaceToStayDTO, PlaceToStay> mapPlaceToStayDTOToEntity() {
		return (input) -> {
			PlaceToStay p = new PlaceToStay();
			p.setDescription(input.getDescription());
			p.setTitle(input.getTitle());
			p.setImage(toImageEntity(input.getImage()));
			p.setSuggestItinerary(toSuggestItineraryEntity(input.getSuggestItinerary()));
			return p;
		};
	}

	@Bean
	@Scope("singleton")
	public Mapper<PlaceToStay, PlaceToStayDTO> mapPlaceToStayEntityToDTO() {
		return (input) -> PlaceToStayDTO.builder()
				.withDescription(input.getDescription())
				.withTitle(input.getTitle())
				.withImage(toImageDTO(input.getImage()))
				.withSuggestItinerary(toSuggestItineraryDTO(input.getSuggestItinerary()))
				.build();
	}
	@Bean
	@Scope("singleton")
	public Mapper<PlaceToStay, PlaceToStayResponseDTO> mapPlaceToStayEntityToResponse() {
		return (input) -> PlaceToStayResponseDTO.builder()
				.withDescription(input.getDescription())
				.withTitle(input.getTitle())
				.withImage(toImageDTO(input.getImage()))
				.withSuggestItinerary(toSuggestItineraryDTO(input.getSuggestItinerary()))
				.build();
	}
	@Bean
	@Scope("singleton")
	public Mapper<FoodDTO, Food> mapFoodDTOToEntity() {
		return (input) -> {
			Food f = new Food();
			f.setDescription(input.getDescription());
			f.setTitle(input.getTitle());
			f.setImage(toImageEntity(input.getImage()));
			f.setSuggestItinerary(toSuggestItineraryEntity(input.getSuggestItinerary()));
			return f;
		};
	}

	@Bean
	@Scope("singleton")
	public Mapper<Food, FoodDTO> mapFoodEntityToDTO() {
		return (input) -> FoodDTO.builder()
				.withDescription(input.getDescription())
				.withTitle(input.getTitle())
				.withImage(toImageDTO(input.getImage()))
				.withSuggestItinerary(toSuggestItineraryDTO(input.getSuggestItinerary()))
				.build();
	}
	@Bean
	@Scope("singleton")
	public Mapper<Food, FoodResponseDTO> mapFoodEntityToResponse() {
		return (input) -> FoodResponseDTO.builder()
				.withDescription(input.getDescription())
				.withTitle(input.getTitle())
				.withImage(toImageDTO(input.getImage()))
				.withSuggestItinerary(toSuggestItineraryDTO(input.getSuggestItinerary()))
				.build();
	}
	// Image Mapper
	@Bean
	@Scope("singleton")
	public Mapper<ImageDTO, Image> mapImageDTOToEntity() {
		return (input) -> {
			Image image = new Image();
			image.setImageURL(input.getImageURL());
			image.setDescription(input.getDescription());
			return image;
		};
	}

	@Bean
	@Scope("singleton")
	public Mapper<Image, ImageResponseDTO> mapImageEntityToResponse() {
		return (input) -> ImageResponseDTO.builder()
				.withImageURL(input.getImageURL())
				.withDescription(input.getDescription())
				.build();
	}

	// Role Mapper
	@Bean
	@Scope("singleton")
	public Mapper<RoleEntityDTO, RoleEntity> mapRoleEntityDTOToEntity() {
		return (input) -> RoleEntity.builder()
				.withRoleType(input.getRoleType())
				.build();
	}

	@Bean
	@Scope("singleton")
	public Mapper<RoleEntity, RoleEntityDTO> mapRoleEntityToDTO() {
		return (input) -> RoleEntityDTO.builder()
				.withRoleType(input.getRoleType())
				.build();
	}
	@Bean
	@Scope("singleton")
	public Mapper<RoleEntity, RolesResponseDTO> mapRoleEntityToResponse() {
		return (input) -> RolesResponseDTO.builder()
				.withRoleType(input.getRoleType())
				.build();
	}


	// Helper methods
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

	private User toUserEntity(RegisteredUserDTO userDTO) {
		if (userDTO == null) {
			return null; // O gestisci l'errore come appropriato
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


	private StepRequestDTO toStepRequestDTO(Step step) {
		return StepRequestDTO.builder()
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
				.withComments(step.getComments().stream().map(this::toCommentRequestDTO).collect(Collectors.toList()))
				.withImages(step.getImages().stream().map(this::toImageDTO).collect(Collectors.toList()))
				.build();
	}

	private Step toStepEntity(StepRequestDTO stepRequestDTO) {
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

	private CommentRequestDTO toCommentRequestDTO(Comment comment) {
		return CommentRequestDTO.builder()
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

	private Comment toCommentEntity(CommentRequestDTO commentRequestDTO) {
		Comment comment = new Comment();
		comment.setText(commentRequestDTO.getText());
		comment.setDate(commentRequestDTO.getDate());
		comment.setStep(commentRequestDTO.getStep() != null ? toStepEntity(commentRequestDTO.getStep()) : null);
		comment.setUser(commentRequestDTO.getUser() != null ? toUserEntity(commentRequestDTO.getUser()) : null);
		return comment;
	}

	private ImageDTO toImageDTO(Image image) {
		if (image == null) {
			return null;
		}
		return ImageDTO.builder()
				.withImageURL(image.getImageURL())
				.withDescription(image.getDescription())
				.build();
	}

	private Image toImageEntity(ImageDTO imageDTO) {
		if (imageDTO == null) {
			return null;
		}
		Image image = new Image();
		image.setImageURL(imageDTO.getImageURL());
		image.setDescription(imageDTO.getDescription());
		return image;
	}

	private TripRequestDTO toTripDTO(Trip trip) {
		return TripRequestDTO.builder()
				.withTitle(trip.getTitle())
				.withDescription(trip.getDescription())
				.withLikes(trip.getLikes())
				.withStartDate(trip.getStartDate())
				.withEndDate(trip.getEndDate())
				.withStatus(trip.getStatus())
				.withPrivacy(trip.getPrivacy())
				.withUser(trip.getUser() != null ? toRegisteredUserDTO(trip.getUser()) : null)
				.withSteps(trip.getSteps() != null ? trip.getSteps().stream().map(this::toStepRequestDTO).collect(Collectors.toList()) : null)
				.withCoverImage(trip.getCoverImage() != null ? toImageDTO(trip.getCoverImage()) : null)
				.build();
	}


	private TripResponseDTO toTripResponseDTO(Trip trip) {
		return TripResponseDTO.builder()
				.withTitle(trip.getTitle())
				.withDescription(trip.getDescription())
				.withLikes(trip.getLikes())
				.withStartDate(trip.getStartDate())
				.withEndDate(trip.getEndDate())
				.withStatus(trip.getStatus())
				.withPrivacy(trip.getPrivacy())
				.withUser(toRegisteredUserDTO(trip.getUser()))
				.withSteps(trip.getSteps().stream().map(this::toStepRequestDTO).collect(Collectors.toList()))
				.withCoverImage(toImageDTO(trip.getCoverImage()))
				.build();
	}

	private TripRequestDTO toTripRequestDTO(Trip trip) {
		return TripRequestDTO.builder()
				.withTitle(trip.getTitle())
				.withDescription(trip.getDescription())
				.withLikes(trip.getLikes())
				.withStartDate(trip.getStartDate())
				.withEndDate(trip.getEndDate())
				.withStatus(trip.getStatus())
				.withPrivacy(trip.getPrivacy())
				.withUser(trip.getUser() != null ? toRegisteredUserDTO(trip.getUser()) : null)
				.withSteps(trip.getSteps() != null ? trip.getSteps().stream().map(this::toStepRequestDTO).collect(Collectors.toList()) : null)
				.withCoverImage(trip.getCoverImage() != null ? toImageDTO(trip.getCoverImage()) : null)
				.build();
	}

	private Trip toTripEntity(TripRequestDTO tripRequestDTO) {
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

	private ToDoDTO toToDoDTO(ToDo toDo) {
		return ToDoDTO.builder()
				.withDescription(toDo.getDescription())
				.withTitle(toDo.getTitle())
				.withImage(toImageDTO(toDo.getImage()))
				.withSuggestItinerary(toSuggestItineraryDTO(toDo.getSuggestItinerary()))
				.build();
	}

	private ToDo toToDoEntity(ToDoDTO toDoDTO) {
		return ToDo.builder()
				.withDescription(toDoDTO.getDescription())
				.withTitle(toDoDTO.getTitle())
				.withImage(toImageEntity(toDoDTO.getImage()))
				.withSuggestItinerary(toSuggestItineraryEntity(toDoDTO.getSuggestItinerary()))
				.build();
	}

	private RestaurantDTO toRestaurantDTO(Restaurant restaurant) {
		return RestaurantDTO.builder()
				.withDescription(restaurant.getDescription())
				.withTitle(restaurant.getTitle())
				.withImage(toImageDTO(restaurant.getImage()))
				.withSuggestItinerary(toSuggestItineraryDTO(restaurant.getSuggestItinerary()))
				.build();
	}

	private Restaurant toRestaurantEntity(RestaurantDTO restaurantDTO) {
		return Restaurant.builder()
				.withDescription(restaurantDTO.getDescription())
				.withTitle(restaurantDTO.getTitle())
				.withImage(toImageEntity(restaurantDTO.getImage()))
				.withSuggestItinerary(toSuggestItineraryEntity(restaurantDTO.getSuggestItinerary()))
				.build();
	}

	private QuickFactsDTO toQuickFactsDTO(QuickFacts quickFacts) {
		return QuickFactsDTO.builder()
				.withDescription(quickFacts.getDescription())
				.withTitle(quickFacts.getTitle())
				.withImage(toImageDTO(quickFacts.getImage()))
				.withSuggestItinerary(toSuggestItineraryDTO(quickFacts.getSuggestItinerary()))
				.build();
	}

	private QuickFacts toQuickFactsEntity(QuickFactsDTO quickFactsDTO) {
		return QuickFacts.builder()
				.withDescription(quickFactsDTO.getDescription())
				.withTitle(quickFactsDTO.getTitle())
				.withImage(toImageEntity(quickFactsDTO.getImage()))
				.withSuggestItinerary(toSuggestItineraryEntity(quickFactsDTO.getSuggestItinerary()))
				.build();
	}

	private PlaceToStayDTO toPlaceToStayDTO(PlaceToStay placeToStay) {
		return PlaceToStayDTO.builder()
				.withDescription(placeToStay.getDescription())
				.withTitle(placeToStay.getTitle())
				.withImage(toImageDTO(placeToStay.getImage()))
				.withSuggestItinerary(toSuggestItineraryDTO(placeToStay.getSuggestItinerary()))
				.build();
	}

	private PlaceToStay toPlaceToStayEntity(PlaceToStayDTO placeToStayDTO) {
		return PlaceToStay.builder()
				.withDescription(placeToStayDTO.getDescription())
				.withTitle(placeToStayDTO.getTitle())
				.withImage(toImageEntity(placeToStayDTO.getImage()))
				.withSuggestItinerary(toSuggestItineraryEntity(placeToStayDTO.getSuggestItinerary()))
				.build();
	}

	private FoodDTO toFoodDTO(Food food) {
		return FoodDTO.builder()
				.withDescription(food.getDescription())
				.withTitle(food.getTitle())
				.withImage(toImageDTO(food.getImage()))
				.withSuggestItinerary(toSuggestItineraryDTO(food.getSuggestItinerary()))
				.build();
	}

	private Food toFoodEntity(FoodDTO foodDTO) {
		return Food.builder()
				.withDescription(foodDTO.getDescription())
				.withTitle(foodDTO.getTitle())
				.withImage(toImageEntity(foodDTO.getImage()))
				.withSuggestItinerary(toSuggestItineraryEntity(foodDTO.getSuggestItinerary()))
				.build();
	}

	private SuggestItineraryDTO toSuggestItineraryDTO(SuggestItinerary suggestItinerary) {
		return SuggestItineraryDTO.builder()
				.withName(suggestItinerary.getName())
				.withDescription(suggestItinerary.getDescription())
				.withLocation(suggestItinerary.getLocation())
				.withLikes(suggestItinerary.getLikes())
				.withSuggest(String.valueOf(suggestItinerary.getSuggest()))
				.withUser(toRegisteredUserDTO(suggestItinerary.getUser()))
				.withImage(toImageDTO(suggestItinerary.getImage()))
				.withToDo(suggestItinerary.getToDo().stream().map(this::toToDoDTO).collect(Collectors.toList()))
				.withRestaurant(suggestItinerary.getRestaurant().stream().map(this::toRestaurantDTO).collect(Collectors.toList()))
				.withQuickFacts(suggestItinerary.getQuickFacts().stream().map(this::toQuickFactsDTO).collect(Collectors.toList()))
				.withPlaceToStay(suggestItinerary.getPlaceToStay().stream().map(this::toPlaceToStayDTO).collect(Collectors.toList()))
				.withFood(suggestItinerary.getFood().stream().map(this::toFoodDTO).collect(Collectors.toList()))
				.build();
	}

	private SuggestItinerary toSuggestItineraryEntity(SuggestItineraryDTO suggestItineraryDTO) {
		SuggestItinerary suggestItinerary = new SuggestItinerary();
		suggestItinerary.setName(suggestItineraryDTO.getName());
		suggestItinerary.setDescription(suggestItineraryDTO.getDescription());
		suggestItinerary.setLocation(suggestItineraryDTO.getLocation());
		suggestItinerary.setLikes(suggestItineraryDTO.getLikes());
		suggestItinerary.setSuggest(Collections.singletonList(suggestItineraryDTO.getSuggest()));
		suggestItinerary.setUser(toUserEntity(suggestItineraryDTO.getUser()));
		suggestItinerary.setImage(toImageEntity(suggestItineraryDTO.getImage()));
		suggestItinerary.setToDo(suggestItineraryDTO.getToDo().stream().map(this::toToDoEntity).collect(Collectors.toList()));
		suggestItinerary.setRestaurant(suggestItineraryDTO.getRestaurant().stream().map(this::toRestaurantEntity).collect(Collectors.toList()));
		suggestItinerary.setQuickFacts(suggestItineraryDTO.getQuickFacts().stream().map(this::toQuickFactsEntity).collect(Collectors.toList()));
		suggestItinerary.setPlaceToStay(suggestItineraryDTO.getPlaceToStay().stream().map(this::toPlaceToStayEntity).collect(Collectors.toList()));
		suggestItinerary.setFood(suggestItineraryDTO.getFood().stream().map(this::toFoodEntity).collect(Collectors.toList()));
		return suggestItinerary;
	}


}
