package com.caminando.Caminando.businesslayer.services.interfaces.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.PositionRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.StepRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.StepResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.TripResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.travel.Step;
import com.caminando.Caminando.presentationlayer.api.models.travel.StepModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StepService {

    StepResponseDTO delete(Long id);
    Page<StepResponseDTO> getAll(Pageable p);
    StepResponseDTO getById(Long id);
    StepResponseDTO update(Long id, StepRequestDTO request);
    StepResponseDTO save(StepRequestDTO stepRequestDTO, Long tripId, PositionRequestDTO position);

    StepResponseDTO saveImage (Long id, MultipartFile[] file) throws IOException;;
}
