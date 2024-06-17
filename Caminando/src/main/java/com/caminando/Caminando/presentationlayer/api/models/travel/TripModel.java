package com.caminando.Caminando.presentationlayer.api.models.travel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TripModel(

        @NotBlank(message = "Il titolo del viaggio non può essere omesso")
        String title,
        String description,
        @NotNull(message = "La data di inizio non può essere nulla")
        LocalDate startDate,
        @NotNull(message = "La data di inizio non può essere nulla")
        LocalDate endDate,
        @NotNull(message = "Lo status non può essere nullo")
        String status,
        @NotNull(message = "La privacy  non può essere nullo")
        String privacy
) {
}
