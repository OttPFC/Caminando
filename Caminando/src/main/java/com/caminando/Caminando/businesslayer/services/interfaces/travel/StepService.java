package com.caminando.Caminando.businesslayer.services.interfaces.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.StepDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.TripDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.travel.Step;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.caminando.Caminando.presentationlayer.api.models.travel.StepModel;
import com.caminando.Caminando.presentationlayer.api.models.travel.TripModel;

public interface StepService extends CRUDService<Step, StepDTO> {
    StepDTO mapEntityToDTO(Step step);

    Step createStep(StepModel model, Long tripId, Long positionId);
}
