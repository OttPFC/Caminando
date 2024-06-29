package com.caminando.Caminando.businesslayer.services.dto.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import lombok.*;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class FoodResponseDTO extends BaseDTO {
    private String title;
    private String description;
    private ImageDTO image;
    private SuggestItineraryDTO suggestItinerary;
}