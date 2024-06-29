package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.PositionRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.StepRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.StepResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.TripRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.StepService;
import com.caminando.Caminando.datalayer.entities.travel.Position;
import com.caminando.Caminando.datalayer.entities.travel.Step;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import com.caminando.Caminando.datalayer.repositories.travel.PositionRepository;
import com.caminando.Caminando.datalayer.repositories.travel.StepRepository;
import com.caminando.Caminando.datalayer.repositories.travel.TripRepository;
import com.caminando.Caminando.presentationlayer.api.models.travel.StepModel;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
public class StepServiceImpl implements StepService {

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private Mapper<StepRequestDTO, Step> stepDTOToEntityMapper;

    @Autowired
    private Mapper<Step, StepRequestDTO> stepEntityToDTOMapper;

    @Autowired
    private Mapper<Step, StepResponseDTO> stepEntityToResponseMapper;

    @Autowired
    private Mapper<User, RegisteredUserDTO> userEntityToUserDTOMapper;

    @Autowired
    private Mapper<Trip, TripRequestDTO> tripMapperToDTO;

    @Autowired
    private Mapper<Position, PositionRequestDTO> positionToDTO;

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
    public StepResponseDTO save(StepRequestDTO stepRequestDTO) {
        Step step = stepDTOToEntityMapper.map(stepRequestDTO);
        Step savedStep = stepRepository.save(step);
        return stepEntityToResponseMapper.map(savedStep);
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
    @Transactional
    public StepResponseDTO createStep(StepRequestDTO stepRequestDTO, Long tripId, Long positionId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Trip trip = tripRepository.findByIdAndUserId(tripId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Trip not found or not accessible by this user"));

        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new EntityNotFoundException("Position not found"));

        PositionRequestDTO positionRequestDTO = positionToDTO.map(position);

        StepRequestDTO newStepRequestDTO = StepRequestDTO.builder()
                .withDescription(stepRequestDTO.getDescription())
                .withArrivalDate(stepRequestDTO.getArrivalDate())
                .withDepartureDate(stepRequestDTO.getDepartureDate())
                .withTrip(tripMapperToDTO.map(trip))
                .withComments(new ArrayList<>())
                .withImages(new ArrayList<>())
                .withPosition(positionRequestDTO)
                .build();

        Step newStep = stepDTOToEntityMapper.map(newStepRequestDTO);
        trip.getSteps().add(newStep);

        Step savedStep = stepRepository.save(newStep);
        return stepEntityToResponseMapper.map(savedStep);
    }


}

