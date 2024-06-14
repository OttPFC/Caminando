package com.caminando.Caminando.businesslayer.services.dto;

import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class FoodDTO extends BaseDTO{
    private String title;
    private String description;
    private Image image;
    private SuggestItinerary suggestItinerary;
}
