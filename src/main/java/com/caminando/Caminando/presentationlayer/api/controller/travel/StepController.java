package com.caminando.Caminando.presentationlayer.api.controller.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.StepDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.StepService;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.TripService;
import com.caminando.Caminando.datalayer.entities.travel.Step;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import com.caminando.Caminando.presentationlayer.api.exceptions.ApiValidationException;
import com.caminando.Caminando.presentationlayer.api.models.travel.StepModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/steps")
public class StepController {
    @Autowired
    private StepService stepService;

    @Autowired
    private TripService tripService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/trip/{tripId}/position/{positionId}")
    public ResponseEntity<?> createStep(@PathVariable Long tripId, @PathVariable Long positionId, @RequestBody @Validated StepModel model, BindingResult validator) {
        if (validator.hasErrors()) {
            return ResponseEntity.badRequest().body(validator.getAllErrors());
        }

        try {
            Step createdStep = stepService.createStep(model, tripId, positionId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStep);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StepDTO> getStepById(@PathVariable Long id) {
        Step step = stepService.getById(id);
        if (step != null) {
            StepDTO stepDTO = stepService.mapEntityToDTO(step);
            return new ResponseEntity<>(stepDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<Page<Step>> getAllSteps(Pageable pageable) {
        Page<Step> steps = stepService.getAll(pageable);
        return new ResponseEntity<>(steps, HttpStatus.OK);
    }
}
