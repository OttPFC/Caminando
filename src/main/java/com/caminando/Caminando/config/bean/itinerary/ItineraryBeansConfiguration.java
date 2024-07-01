package com.caminando.Caminando.config.bean.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.*;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.*;
import com.caminando.Caminando.datalayer.entities.travel.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
public class ItineraryBeansConfiguration {

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
}
