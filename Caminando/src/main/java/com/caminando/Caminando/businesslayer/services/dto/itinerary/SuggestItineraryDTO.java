package com.caminando.Caminando.businesslayer.services.dto.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.*;
import com.caminando.Caminando.datalayer.entities.travel.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;
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
    private List<QuickFatcsDTO> quickFacts;
    private List<PlaceToStayDTO> placeToStay;
    private List<FoodDTO> food;
}
