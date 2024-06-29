package com.caminando.Caminando.presentationlayer.api.controller.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.SuggestItineraryDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.SuggestItineraryResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.SuggestItineraryService;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import com.caminando.Caminando.presentationlayer.api.exceptions.ApiValidationException;
import com.caminando.Caminando.presentationlayer.api.models.itinerary.SuggestItineraryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itinerary")
public class SuggestItineraryController {

    @Autowired
    private SuggestItineraryService itService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper<User, RegisteredUserDTO> mapUserEntityToRegisteredUser;

    @PostMapping
    public ResponseEntity<SuggestItineraryResponseDTO> createItinerary(@RequestBody @Validated SuggestItineraryModel model, BindingResult validator) {
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        RegisteredUserDTO userDTO = mapUserEntityToRegisteredUser.map(user);

        SuggestItineraryDTO suggestItineraryDTO = SuggestItineraryDTO.builder()
                .withName(model.name())
                .withDescription(model.description())
                .withLocation(model.location())
                .withUser(userDTO)
                .build();

        SuggestItineraryResponseDTO createdItinerary = itService.save(suggestItineraryDTO);
        return new ResponseEntity<>(createdItinerary, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuggestItineraryResponseDTO> updateItinerary(@PathVariable Long id, @RequestBody SuggestItineraryDTO dto) {
        SuggestItineraryResponseDTO updatedItinerary = itService.update(id, dto);
        return updatedItinerary != null ? new ResponseEntity<>(updatedItinerary, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItinerary(@PathVariable Long id) {
        SuggestItineraryResponseDTO deletedItinerary = itService.delete(id);
        return deletedItinerary != null ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
