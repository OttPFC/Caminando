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
@EqualsAndHashCode(callSuper = true)
@Data
@Builder(setterPrefix = "with")
public class SuggestItinerary extends BaseEntity {

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String description;

    @Column(length = 50, nullable = false)
    private String location;

    @Builder.Default
    private LocalDate createdDate = LocalDate.now();

    @Builder.Default
    private LocalDate modifyDate = LocalDate.now();

    private Integer likes;

    @ElementCollection
    @CollectionTable(name = "itinerary_suggestions", joinColumns = @JoinColumn(name = "itinerary_id"))
    @Column(name = "suggest")
    private List<String> suggest;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany(mappedBy = "suggestItinerary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ToDo> toDo;

    @OneToMany(mappedBy = "suggestItinerary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Restaurant> restaurant;

    @OneToMany(mappedBy = "suggestItinerary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuickFacts> quickFacts;

    @OneToMany(mappedBy = "suggestItinerary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlaceToStay> placeToStay;

    @OneToMany(mappedBy = "suggestItinerary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Food> food;
}
