package com.caminando.Caminando.businesslayer.services.interfaces.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.PositionDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.travel.Position;
import com.caminando.Caminando.presentationlayer.api.models.travel.PositionModel;

public interface PositionService extends CRUDService<Position, PositionDTO> {
    PositionDTO getNomeLocalitaAndTimestampById(Long id);

    PositionDTO mapEntityToDTO(Position position);

    Position save(PositionModel model);
}
