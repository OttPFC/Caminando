package com.caminando.Caminando.datalayer.entities.itinerary;

import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.*;
import com.caminando.Caminando.datalayer.entities.travel.BaseEntity;
import com.caminando.Caminando.datalayer.entities.travel.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "itinerary")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Builder(setterPrefix = "with")
public class SuggestItinerary extends BaseEntity {

    private String name;
    private String description;
    private String location;
    private LocalDate createdDate = LocalDate.now();
    private LocalDate modifyDate = LocalDate.now();
    private Integer likes;
    private String suggest;

    @OneToOne
    private User user;
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany(mappedBy = "suggestItinerary")
    private List<ToDo> toDo;
    @OneToMany(mappedBy = "suggestItinerary")
    private List<Restaurant> restaurant;
    @OneToMany(mappedBy = "suggestItinerary")
    private List<QuickFacts> quickFacts;
    @OneToMany(mappedBy = "suggestItinerary")
    private List<PlaceToStay> placeToStay;
    @OneToMany(mappedBy = "suggestItinerary")
    private List<Food> food;


}
