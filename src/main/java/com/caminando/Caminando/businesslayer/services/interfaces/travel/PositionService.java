package com.caminando.Caminando.businesslayer.services.interfaces.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.PositionRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.PositionResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.travel.Position;
import com.caminando.Caminando.presentationlayer.api.models.travel.PositionModel;

public interface PositionService extends CRUDService<PositionResponseDTO, PositionRequestDTO> {
    PositionRequestDTO getNomeLocalitaAndTimestampById(Long id);
    PositionResponseDTO savePosition(PositionRequestDTO positionRequestDTO);
}
