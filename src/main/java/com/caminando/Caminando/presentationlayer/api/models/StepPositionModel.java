package com.caminando.Caminando.presentationlayer.api.models;

import com.caminando.Caminando.presentationlayer.api.models.travel.PositionModel;
import com.caminando.Caminando.presentationlayer.api.models.travel.StepModel;

public record StepPositionModel(
        StepModel step,
        PositionModel position
) {

}
