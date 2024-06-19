package com.caminando.Caminando.presentationlayer.api.models.travel;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

public record StepModel(

        @NotNull(message = "La descrizione non può essere nulla")
        String description,

        @NotNull(message = "I likes non possono essere nulli")
        Integer likes,

        @NotNull(message = "La data di arrivo non può essere nulla")
        LocalDate arrivalDate,

        @NotNull(message = "La data di partenza non può essere nulla")
        LocalDate departureDate
) {
}
