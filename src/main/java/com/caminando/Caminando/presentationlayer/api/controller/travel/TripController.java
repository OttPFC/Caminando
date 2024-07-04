package com.caminando.Caminando.presentationlayer.api.controller.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.TripRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.TripResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.TripService;
import com.caminando.Caminando.datalayer.entities.enums.Privacy;
import com.caminando.Caminando.datalayer.entities.enums.Status;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import com.caminando.Caminando.presentationlayer.api.exceptions.ApiValidationException;
import com.caminando.Caminando.presentationlayer.api.exceptions.NotFoundException;
import com.caminando.Caminando.presentationlayer.api.models.travel.TripModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/trips")
@Slf4j
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    Mapper<User, RegisteredUserDTO> mapUserEntity2RegisteredUser;

    @PostMapping
    public ResponseEntity<TripResponseDTO> save(@RequestBody @Validated TripModel model, BindingResult validator) {
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }
        log.info("TripModel received: {}", model);

        try {
            String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            User user = userRepository.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
            RegisteredUserDTO userDTO = mapUserEntity2RegisteredUser.map(user);

            TripRequestDTO tripRequestDto = TripRequestDTO.builder()
                    .withTitle(model.title())
                    .withDescription(model.description())
                    .withStartDate(model.startDate())
                    .withEndDate(model.endDate())
                    .withLikes(0)
                    .withStatus(Status.valueOf(model.status()))
                    .withPrivacy(Privacy.valueOf(model.privacy()))
                    .withUser(userDTO)
                    .build();
            log.info("TripRequestDTO built: {}", tripRequestDto);

            TripResponseDTO trip = tripService.save(tripRequestDto);
            log.info("TripResponseDTO saved: {}", trip);

            return new ResponseEntity<>(trip, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            log.error("Error saving trip", ex);
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }





    @GetMapping("/{id}")
    public ResponseEntity<TripResponseDTO> getTripById(@PathVariable Long id) {
        TripResponseDTO trip = tripService.getById(id);
        if (trip != null) {
            return new ResponseEntity<>(trip, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Page<TripResponseDTO>> getAllTrips(Pageable pageable) {
        Page<TripResponseDTO> allTrips = tripService.getAll(pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Viaggi", String.valueOf(allTrips.getTotalElements()));
        return new ResponseEntity<>(allTrips, headers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripResponseDTO> updateTrip(@PathVariable Long id, @RequestBody TripRequestDTO tripModified) {
        TripResponseDTO trip = tripService.update(id, tripModified);
        return new ResponseEntity<>(trip, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        TripResponseDTO trip = tripService.delete(id);
        if (trip != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<TripResponseDTO> uploadTripImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            TripResponseDTO tripResponseDTO = tripService.saveImage(id, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(tripResponseDTO);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
