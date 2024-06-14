package com.caminando.Caminando.businesslayer.services.dto;

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
public class SuggestItineraryDTO extends BaseDTO{
    private String name;
    private String description;
    private String location;
    private LocalDate createdDate = LocalDate.now();
    private LocalDate modifyDate = LocalDate.now();
    private Integer likes;
    private String suggest;
    private User user;
    private Image image;
    private List<ToDo> toDo;
    private List<Restaurant> restaurant;
    private List<QuickFacts> quickFacts;
    private List<PlaceToStay> placeToStay;
    private List<Food> food;
}
