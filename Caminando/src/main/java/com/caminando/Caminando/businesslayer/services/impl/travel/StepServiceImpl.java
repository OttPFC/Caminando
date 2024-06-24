package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.PositionDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.StepDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.TripDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.PositionService;
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
    PositionRepository positionRepository;

    @Autowired
    Mapper<Position, PositionDTO> positionToDTO;
    @Autowired
    private Mapper<StepDTO, Step> stepDTOToEntityMapper;

    @Autowired
    private Mapper<Step, StepDTO> stepEntityToDTOMapper;
    @Autowired
    private Mapper<User, RegisteredUserDTO> userEntityToUserDTOMapper;

    @Autowired
    private Mapper<Trip, TripDTO> tripMapperToDTO;

    @Override
    public Page<Step> getAll(Pageable pageable) {
        return stepRepository.findAll(pageable);
    }

    @Override
    public Step getById(Long id) {
        Optional<Step> step = stepRepository.findById(id);
        return step.orElse(null);
    }

    @Override
    @Transactional
    public Step save(StepDTO stepDTO) {
        Step step = stepDTOToEntityMapper.map(stepDTO);
        return stepRepository.save(step);
    }

    @Override
    @Transactional
    public Step update(Long id, Step updatedStep) {
        Optional<Step> optionalStep = stepRepository.findById(id);
        if (optionalStep.isPresent()) {
            Step existingStep = optionalStep.get();
            existingStep.setDescription(updatedStep.getDescription());
            existingStep.setLikes(updatedStep.getLikes());
            existingStep.setArrivalDate(updatedStep.getArrivalDate());
            existingStep.setDepartureDate(updatedStep.getDepartureDate());
            return stepRepository.save(existingStep);
        }
        return null;
    }

    @Override
    @Transactional
    public Step delete(Long id) {
        Optional<Step> optionalStep = stepRepository.findById(id);
        if (optionalStep.isPresent()) {
            Step step = optionalStep.get();
            stepRepository.delete(step);
            return step;
        }
        return null;
    }

    @Override
    public StepDTO mapEntityToDTO(Step step) {
        return null;
    }


    @Override
    @Transactional
    public Step createStep(StepModel model, Long tripId, Long positionId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Trip trip = tripRepository.findByIdAndUserId(tripId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Trip not found or not accessible by this user"));

        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new EntityNotFoundException("Position not found"));

        PositionDTO positionDTO = positionToDTO.map(position);

        StepDTO stepDTO = StepDTO.builder()
                .withDescription(model.description())
                .withArrivalDate(model.arrivalDate())
                .withDepartureDate(model.departureDate())
                .withTrip(tripMapperToDTO.map(trip))
                .withComments(new ArrayList<>())
                .withImages(new ArrayList<>())
                .withPosition(positionDTO)
                .build();

        Step newStep = stepDTOToEntityMapper.map(stepDTO);
        trip.getSteps().add(newStep);

        return stepRepository.save(newStep);
    }


}
