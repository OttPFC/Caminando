package com.caminando.Caminando.datalayer.entities.itinerary.entityplace;

import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "food")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder(setterPrefix = "with")
public class Food extends SuggestPlacesBase {
    private String title;
    private String description;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;
    @ManyToOne
    @JoinColumn(name = "suggest_itinerary_id")
    private SuggestItinerary suggestItinerary;
}
