package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.*;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.StepService;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.travel.Position;
import com.caminando.Caminando.datalayer.entities.travel.Step;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import com.caminando.Caminando.datalayer.repositories.travel.PositionRepository;
import com.caminando.Caminando.datalayer.repositories.travel.StepRepository;
import com.caminando.Caminando.datalayer.repositories.travel.TripRepository;
import com.caminando.Caminando.presentationlayer.api.exceptions.NotFoundException;
import com.caminando.Caminando.presentationlayer.api.models.travel.StepModel;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StepServiceImpl implements StepService {

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private Mapper<StepRequestDTO, Step> stepDTOToEntityMapper;

    @Autowired
    private Mapper<ImageDTO, Image> mapImageDTOToEntity;

    @Autowired
    private Mapper<Step, StepResponseDTO> stepEntityToResponseMapper;


    @Autowired
    private Mapper<Trip, TripResponseDTO> tripMapperToDTOResponse;


    @Autowired
    private Mapper<PositionRequestDTO, Position> positionDTOToEntityMapper;


    @Value("${CLOUDINARY_URL}")
    private String cloudinaryUrl;

    @Override
    public Page<StepResponseDTO> getAll(Pageable pageable) {
        Page<Step> steps = stepRepository.findAll(pageable);
        return steps.map(stepEntityToResponseMapper::map);
    }

    @Override
    public StepResponseDTO getById(Long id) {
        Optional<Step> step = stepRepository.findById(id);
        return step.map(stepEntityToResponseMapper::map).orElse(null);
    }

    @Override
    @Transactional
    public StepResponseDTO save(StepRequestDTO stepRequestDTO, Long tripId, PositionRequestDTO positionRequestDTO) {
        // Find the trip and ensure it exists
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found"));

        // Map and save the position
        Position position = positionDTOToEntityMapper.map(positionRequestDTO);
        position = positionRepository.save(position);

        // Map and save the step
        Step step = stepDTOToEntityMapper.map(stepRequestDTO);
        step.setTrip(trip); // Ensure the step has the saved trip
        step.setPosition(position); // Link the saved position to the step

        step = stepRepository.save(step);

        // Update position with the saved step (if necessary)
        position.setStep(step);
        positionRepository.save(position);

        return stepEntityToResponseMapper.map(step);
    }


    @Override
    @Transactional
    public StepResponseDTO update(Long id, StepRequestDTO stepRequestDTO) {
        Optional<Step> optionalStep = stepRepository.findById(id);
        if (optionalStep.isPresent()) {
            Step existingStep = optionalStep.get();
            // Copia i campi dal DTO all'entità
            existingStep.setDescription(stepRequestDTO.getDescription());
            existingStep.setLikes(stepRequestDTO.getLikes());
            existingStep.setArrivalDate(stepRequestDTO.getArrivalDate());
            existingStep.setDepartureDate(stepRequestDTO.getDepartureDate());
            Step updatedStep = stepRepository.save(existingStep);
            return stepEntityToResponseMapper.map(updatedStep);
        }
        return null;
    }

    @Override
    @Transactional
    public StepResponseDTO delete(Long id) {
        Optional<Step> optionalStep = stepRepository.findById(id);
        if (optionalStep.isPresent()) {
            Step step = optionalStep.get();
            stepRepository.delete(step);
            return stepEntityToResponseMapper.map(step);
        }
        return null;
    }


    @Override
    public StepResponseDTO saveImage(Long id, MultipartFile[] files) throws IOException {
        Step step = stepRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
        List<Image> images = step.getImages();
        if (images == null) {
            images = new ArrayList<>();
        }

        for (MultipartFile file : files) {
            var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            if (imageUrl == null) {
                throw new IOException("Failed to upload image to Cloudinary");
            }

            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setImageURL(imageUrl);

            Image image = mapImageDTOToEntity.map(imageDTO);
            image.setStep(step); // Imposta lo step per l'immagine

            log.info("Adding image to step: {}", image);
            images.add(image);
        }

        step.setImages(images);

        log.info("Step before save: {}", step);
        stepRepository.save(step);

        Step savedStep = stepRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        log.info("Step after save: {}", savedStep);

        return stepEntityToResponseMapper.map(savedStep);
    }






}

