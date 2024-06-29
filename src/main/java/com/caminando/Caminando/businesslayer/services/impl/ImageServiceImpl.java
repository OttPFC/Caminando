package com.caminando.Caminando.businesslayer.services.impl;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.ImageService;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.repositories.ImageRepository;
import com.caminando.Caminando.presentationlayer.utility.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private Mapper<ImageDTO, Image> imageDTOToEntityMapper;

    @Autowired
    private Mapper<Image, ImageResponseDTO> imageEntityToResponseMapper;

    @Autowired
    private EntityUtils utils;

    @Override
    public Page<ImageResponseDTO> getAll(Pageable pageable) {
        Page<Image> images = imageRepository.findAll(pageable);
        return images.map(imageEntityToResponseMapper::map);
    }

    @Override
    public ImageResponseDTO getById(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        return image.map(imageEntityToResponseMapper::map).orElse(null);
    }

    @Override
    @Transactional
    public ImageResponseDTO save(ImageDTO imageDTO) {
        Image image = imageDTOToEntityMapper.map(imageDTO);
        Image savedImage = imageRepository.save(image);
        return imageEntityToResponseMapper.map(savedImage);
    }

    @Override
    @Transactional
    public ImageResponseDTO update(Long id, ImageDTO imageDTO) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        if (optionalImage.isPresent()) {
            Image existingImage = optionalImage.get();
            utils.copy(imageDTO, existingImage);
            Image updatedImage = imageRepository.save(existingImage);
            return imageEntityToResponseMapper.map(updatedImage);
        }
        return null;
    }

    @Override
    @Transactional
    public ImageResponseDTO delete(Long id) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();
            imageRepository.delete(image);
            return imageEntityToResponseMapper.map(image);
        }
        return null;
    }
}
