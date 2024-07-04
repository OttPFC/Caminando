package com.caminando.Caminando.presentationlayer.api.models.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.StepRequestDTO;
import com.caminando.Caminando.datalayer.entities.travel.Step;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

public record PositionModel(
        Double latitude,
        Double longitude,
        LocalDate timestamp,
        String nomeLocalita,
        StepRequestDTO step
) {
}
