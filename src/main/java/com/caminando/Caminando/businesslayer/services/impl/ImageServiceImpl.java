package com.caminando.Caminando.businesslayer.services.impl;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.ImageService;
import com.caminando.Caminando.datalayer.entities.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Override
    public Page<Image> getAll(Pageable p) {
        return null;
    }

    @Override
    public Image getById(Long id) {
        return null;
    }

    @Override
    public Image save(ImageDTO e) {
        return null;
    }

    @Override
    public Image update(Long id, Image e) {
        return null;
    }

    @Override
    public Image delete(Long id) {
        return null;
    }
}
