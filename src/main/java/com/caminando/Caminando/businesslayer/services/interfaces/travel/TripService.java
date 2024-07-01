package com.caminando.Caminando.businesslayer.services.interfaces.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.TripRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.TripResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.caminando.Caminando.presentationlayer.api.models.travel.TripModel;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TripService extends CRUDService<TripResponseDTO, TripRequestDTO> {
        Trip getTripByIdAndUserId(Long tripId, Long userId);
TripResponseDTO saveImage (Long id, MultipartFile file) throws IOException;;
}
