package com.caminando.Caminando.presentationlayer.api.models.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.PositionResponseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

public record StepModel(


        String description,

        @NotNull(message = "La data di arrivo non può essere nulla")
        LocalDate arrivalDate,

        @NotNull(message = "La data di partenza non può essere nulla")
        LocalDate departureDate,
        @NotNull(message = "La posizione non può essere nulla")
        PositionResponseDTO position
) {
}
