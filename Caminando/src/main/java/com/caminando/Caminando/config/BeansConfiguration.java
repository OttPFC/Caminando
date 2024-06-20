package com.caminando.Caminando.config;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.*;
import com.caminando.Caminando.businesslayer.services.dto.travel.*;
import com.caminando.Caminando.businesslayer.services.dto.user.*;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.*;
import com.caminando.Caminando.datalayer.entities.travel.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
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
		return (input) -> Position.builder()
				.withLatitude(input.getLatitude())
				.withLongitude(input.getLongitude())
				.withTimestamp(input.getTimestamp())
				.withNomeLocalita(input.getNomeLocalita())
				.build();
	}

	@Bean
	@Scope("singleton")
	public Mapper<Position, PositionDTO> mapPositionEntityToDTO() {
		return (input) -> PositionDTO.builder()
				.withLatitude(input.getLatitude())
				.withLongitude(input.getLongitude())
				.withTimestamp(input.getTimestamp())
				.withNomeLocalita(input.getNomeLocalita())
				.build();
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
//	@Bean
//	@Scope("singleton")
//	public Mapper<TripDTO, Trip> mapTripDTOToEntity() {
//		return (input) -> Trip.builder()
//				.withTitle(input.getTitle())
//				.withDescription(input.getDescription())
//				.withLikes(input.getLikes())
//				.withStartDate(input.getStartDate())
//				.withEndDate(input.getEndDate())
//				.withStatus(input.getStatus())
//				.withPrivacy(input.getPrivacy())
//				.withUser(input.getUser)
//				.withSteps(input.getSteps().stream().map(this::toStepEntity).collect(Collectors.toList()))
//				.withCoverImage(input.getCoverImage())
//				.build();
//	}

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
	public Mapper<ToDoDTO, ToDo> mapToDoDTOToEntity(){
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
	public Mapper<RestaurantDTO, Restaurant> mapRestaurantDTOToEntity(){
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
	public Mapper<QuickFactsDTO, QuickFacts> mapQuickFactsDTOToEntity(){
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
	public Mapper<PlaceToStayDTO, PlaceToStay> mapPlaceToStayDTOToEntity(){
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
	public Mapper<FoodDTO, Food> mapFoodDTOToEntity(){
		return (input) -> {
			Food f = new Food();
			f.setDescription(input.getDescription());
			f.setTitle(input.getTitle());
			f.setImage(toImageEntity(input.getImage()));
			f.setSuggestItinerary(toSuggestItineraryEntity(input.getSuggestItinerary()));
            return f;
		};
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
