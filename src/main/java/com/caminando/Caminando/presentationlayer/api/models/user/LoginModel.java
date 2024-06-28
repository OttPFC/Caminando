package com.caminando.Caminando.presentationlayer.api.models.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginModel(
        @NotBlank(message = "Lo username  non può contenere solo spazi vuoti")
        @Size(max = 20, message ="Lo username può contenere max 20 caratteri")
        String username,
        @NotBlank(message = "La password non può contenere solo spazi vuoti")
        @Size(max = 25, message ="La password può contenere max 25 caratteri")
        String password
) {
}
