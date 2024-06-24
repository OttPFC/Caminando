package com.caminando.Caminando.presentationlayer.api.models.travel;

import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.datalayer.entities.travel.Step;

import java.sql.Timestamp;
import java.time.Instant;

public record PositionModel(
        Double latitude,
        Double longitude,
        Instant timestamp,
        String nomeLocalita,
        Step step,
        RegisteredUserDTO user
) {
}
