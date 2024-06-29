package com.caminando.Caminando.businesslayer.services.interfaces;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.Image;

public interface ImageService extends CRUDService<ImageResponseDTO, ImageDTO> {
}
