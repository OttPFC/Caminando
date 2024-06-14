package com.caminando.Caminando.datalayer.entities;

import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.*;
import com.caminando.Caminando.datalayer.entities.travel.BaseEntity;
import com.caminando.Caminando.datalayer.entities.travel.Step;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.caminando.Caminando.datalayer.entities.travel.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image extends BaseEntity {

    private String imageURL;

    @OneToOne(mappedBy = "profileImage")
    private User user;

    @OneToOne(mappedBy = "coverImage")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "step_id")
    private Step step;

    @OneToOne(mappedBy = "image")
    private SuggestItinerary suggestItinerary;

    @OneToOne(mappedBy = "image")
    private ToDo toDo;
    @OneToOne(mappedBy = "image")
    private Restaurant restaurant;
    @OneToOne(mappedBy = "image")
    private QuickFacts quickFacts;
    @OneToOne(mappedBy = "image")
    private PlaceToStay placeToStay;
    @OneToOne(mappedBy = "image")
    private Food food;
}
