package com.caminando.Caminando.presentationlayer.api.controller.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.*;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.StepService;
import com.caminando.Caminando.datalayer.entities.travel.Position;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import com.caminando.Caminando.datalayer.repositories.travel.PositionRepository;
import com.caminando.Caminando.datalayer.repositories.travel.TripRepository;
import com.caminando.Caminando.presentationlayer.api.exceptions.ApiValidationException;
import com.caminando.Caminando.presentationlayer.api.models.StepPositionModel;
import com.caminando.Caminando.presentationlayer.api.models.travel.PositionModel;
import com.caminando.Caminando.presentationlayer.api.models.travel.StepModel;
import jakarta.persistence.EntityNotFoundException;
import jdk.jfr.Registered;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/steps")
@Slf4j
public class StepController {

    @Autowired
    private StepService stepService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private Mapper<PositionRequestDTO, PositionResponseDTO> requestToResponse;

    @Autowired
    private Mapper<PositionRequestDTO, Position> requestToPosition;

    @Autowired
    private Mapper<Trip, TripResponseDTO> entityToResponse;

    @Autowired
    private Mapper<StepRequestDTO,StepResponseDTO> stepResponseToRequest;

    @Autowired
    private Mapper<User, RegisteredUserDTO> userToRegistered;

    @GetMapping
    public ResponseEntity<Page<StepResponseDTO>> getAllSteps(Pageable pageable) {
        Page<StepResponseDTO> steps = stepService.getAll(pageable);
        return ResponseEntity.ok(steps);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StepResponseDTO> getStepById(@PathVariable Long id) {
        StepResponseDTO step = stepService.getById(id);
        if (step != null) {
            return ResponseEntity.ok(step);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("{id}/create")
    public ResponseEntity<StepResponseDTO> save(@RequestBody @Validated StepPositionModel model, @PathVariable("id") Long Id, BindingResult validator ) {
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }
        log.info("TripModel received: {}", model);
        try {
            String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            User user = userRepository.findOneByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            RegisteredUserDTO userDTO = userToRegistered.map(user);

            Trip trip = tripRepository.findByIdAndUserId(Id, user.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Trip not found or not accessible by this user"));

            // Ensure the trip is saved
            if (trip.getId() == null) {
                trip = tripRepository.save(trip);
            }

            TripResponseDTO tripResponse = entityToResponse.map(trip);

            PositionRequestDTO positionRequestDTO = PositionRequestDTO.builder()
                    .withLatitude(model.position().latitude())
                    .withLongitude(model.position().longitude())
                    .withTimestamp(LocalDate.now())
                    .withNomeLocalita(model.position().nomeLocalita())
                    .withUser(userDTO)
                    .build();
            PositionResponseDTO pResponse = requestToResponse.map(positionRequestDTO);
            log.info("PositionRequestDTO built: {}", positionRequestDTO);

            log.info("position response: {}", pResponse);

            StepRequestDTO stepRequestDTO = StepRequestDTO.builder()
                    .withDescription(model.step().description())
                    .withDepartureDate(model.step().departureDate())
                    .withArrivalDate(model.step().arrivalDate())
                    .withLikes(0)
                    .withPosition(pResponse)
                    .withTrip(tripResponse)
                    .build();

            log.info("StepRequestDTO built: {}", stepRequestDTO);

            StepResponseDTO stepResponse = stepResponseToRequest.map(stepRequestDTO);
            log.info("SteResponse {}", stepResponse);


            positionRequestDTO.setStep(stepResponse);
            stepResponse = stepService.save(stepRequestDTO, Id, positionRequestDTO);

            return new ResponseEntity<>(stepResponse, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            log.info("Error saving step", ex);
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<StepResponseDTO> updateStep(@PathVariable Long id, @RequestBody StepRequestDTO stepRequestDTO) {
        StepResponseDTO updatedStep = stepService.update(id, stepRequestDTO);
        if (updatedStep != null) {
            return ResponseEntity.ok(updatedStep);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StepResponseDTO> deleteStep(@PathVariable Long id) {
        StepResponseDTO deletedStep = stepService.delete(id);
        if (deletedStep != null) {
            return ResponseEntity.ok(deletedStep);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

