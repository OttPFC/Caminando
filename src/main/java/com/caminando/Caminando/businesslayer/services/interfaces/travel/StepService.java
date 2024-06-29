package com.caminando.Caminando.businesslayer.services.interfaces.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.StepRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.StepResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.travel.Step;
import com.caminando.Caminando.presentationlayer.api.models.travel.StepModel;

public interface StepService extends CRUDService<StepResponseDTO, StepRequestDTO> {


    StepResponseDTO createStep(StepRequestDTO stepRequestDTO, Long tripId, Long positionId);
}
