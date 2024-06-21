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

    @PostMapping
    public ResponseEntity<Step> createStep(@PathVariable Long tripId,@RequestBody @Validated StepModel model, BindingResult validator) {
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }

        // Recupera l'utente autenticato
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        // Recupera il trip associato all'utente in base al tripId nel StepModel
        Trip trip = tripService.getTripByIdAndUserId(tripId, user.getId());
        if (trip == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var step = stepService.save(StepDTO.builder()
                .withDescription(model.description())
                        .withArrivalDate(model.arrivalDate())
                .withDepartureDate(model.departureDate())
                        .withTrip(trip)
                        .withComments(new ArrayList<>())
                        .withImages(new ArrayList<>())
                .build());
        return new ResponseEntity<>(step, HttpStatus.CREATED);
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
