package com.caminando.Caminando.presentationlayer.api.models.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserModel(
        @NotBlank(message = "Il nome non può essere vuoto")
        String firstName,
        @NotBlank(message = "Il cognome non può essere vuoto")
        String lastName,
        @NotBlank(message = "Lo username non può contenere spazi vuoti")
        @Size(max = 50, message ="Lo username può contenere un max 50 caratteri")
        String username,
        @Email(message = "Inserisci una email valida")
        String email,
        String city,
        @NotBlank(message = "La password non può contenere solo spazi vuoti")
        @Size(max = 125, message ="La password può contenere un max 25 caratteri")
        String password
) {
}
