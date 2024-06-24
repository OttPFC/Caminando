package com.caminando.Caminando.datalayer.entities.itinerary.entityplace;

import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.datalayer.entities.travel.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "place_to_stay")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder(setterPrefix = "with")
public class PlaceToStay extends BaseEntity {

    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 50, nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;
    @ManyToOne
    @JoinColumn(name = "suggest_itinerary_id")
    private SuggestItinerary suggestItinerary;
}
