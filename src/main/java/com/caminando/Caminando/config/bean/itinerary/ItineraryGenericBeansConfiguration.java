package com.caminando.Caminando.config.bean.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.*;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RoleEntityDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RolesResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.RoleEntity;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.*;
import com.caminando.Caminando.datalayer.entities.travel.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
public class ItineraryGenericBeansConfiguration {

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
    public Mapper<RoleEntity, RoleEntityDTO> mapRoleEntityToDTO2() {
        return (input) -> RoleEntityDTO.builder()
                .withRoleType(input.getRoleType())
                .build();
    }
//    @Bean
//    @Scope("singleton")
//    public Mapper<RoleEntity, RolesResponseDTO> mapRoleEntityToResponse() {
//        return (input) -> RolesResponseDTO.builder()
//                .withRoleType(input.getRoleType())
//                .build();
//    }


    // Helper methods

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
