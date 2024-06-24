package com.caminando.Caminando.presentationlayer.api.models.travel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentModel(
        @NotBlank(message = "Il commento non può essere vuoto")
        @Size(max = 200, message = "Non può contenere più di 200 caratteri")
        String text
) {
}
