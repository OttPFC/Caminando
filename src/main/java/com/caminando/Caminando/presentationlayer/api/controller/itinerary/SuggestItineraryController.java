package com.caminando.Caminando.presentationlayer.api.controller.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.SuggestItineraryDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.SuggestItineraryService;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
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
@RequestMapping("api/itinerary")
public class SuggestItineraryController {

    @Autowired
    private SuggestItineraryService itService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public Mapper<User, RegisteredUserDTO> mapUserEntity2RegisteredUser;

    @PostMapping
    public ResponseEntity<SuggestItinerary> createItinerary(@RequestBody @Validated SuggestItineraryModel model, BindingResult validator){
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        RegisteredUserDTO userDTO = mapUserEntity2RegisteredUser.map(user);

        var itinerary = itService.save(SuggestItineraryDTO.builder()
                .withName(model.name())
                .withDescription(model.description())
                .withLocation(model.location())
                .withUser(userDTO)
                .build());

        return new ResponseEntity<>(itinerary, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuggestItinerary> updateItinerary(@PathVariable Long id, @RequestBody SuggestItinerary itModified){
        var it = itService.update(id, itModified);
        return new ResponseEntity<>(it, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<SuggestItinerary> deleteItinerary(@PathVariable Long id){
        var it = itService.delete(id);
        return new ResponseEntity<>(it, HttpStatus.OK);
    }
}
