package com.caminando.Caminando.presentationlayer.api.models.travel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StepModel(
        @NotNull(message = "Il ")
        String location,
        String description,
        String image,
        String video,
        String distance,
        String duration

) {
}
