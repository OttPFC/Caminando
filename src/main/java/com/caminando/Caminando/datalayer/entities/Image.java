package com.caminando.Caminando.datalayer.entities;

import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.*;
import com.caminando.Caminando.datalayer.entities.travel.BaseEntity;
import com.caminando.Caminando.datalayer.entities.travel.Step;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "image")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Image extends BaseEntity {

    @URL
    private String imageURL;

    private String description;

    @OneToOne(mappedBy = "profileImage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @OneToOne(mappedBy = "coverImage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id")
    @JsonIgnore
    private Step step;

    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private SuggestItinerary suggestItinerary;

    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private ToDo toDo;

    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Restaurant restaurant;

    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private QuickFacts quickFacts;

    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private PlaceToStay placeToStay;

    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Food food;
}
