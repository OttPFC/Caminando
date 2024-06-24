package com.caminando.Caminando.presentationlayer.api.models.itinerary;

import jakarta.validation.constraints.NotBlank;



public record SuggestItineraryModel(
        @NotBlank(message = "Devi inserire il titolo dell'itinerario")
        String name,
        @NotBlank(message = "Inserisci una descizione")
        String description,
        @NotBlank(message = "Devi inserire il luogo dell'itinerario")
        String location

) {
}
