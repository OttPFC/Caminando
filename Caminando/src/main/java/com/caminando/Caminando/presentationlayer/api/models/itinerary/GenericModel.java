package com.caminando.Caminando.presentationlayer.api.models.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.SuggestItineraryDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record GenericModel(
        @NotBlank(message = "Inserisci il titolo")
        String title,
        @NotBlank(message = "Inserisci la descrizione")
        String description,
        ImageDTO image,
        SuggestItineraryDTO suggestItinerary
) {
}
