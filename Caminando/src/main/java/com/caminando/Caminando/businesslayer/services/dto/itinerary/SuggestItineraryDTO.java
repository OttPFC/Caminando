package com.caminando.Caminando.businesslayer.services.dto.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;

import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class SuggestItineraryDTO extends BaseDTO {
    private String name;
    private String description;
    private String location;
    private Integer likes;
    private String suggest;
    private RegisteredUserDTO user;
    private ImageDTO image;
    private List<ToDoDTO> toDo;
    private List<RestaurantDTO> restaurant;
    private List<QuickFactsDTO> quickFacts;
    private List<PlaceToStayDTO> placeToStay;
    private List<FoodDTO> food;
}
