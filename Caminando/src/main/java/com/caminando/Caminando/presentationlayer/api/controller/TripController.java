package com.caminando.Caminando.presentationlayer.api.controller;

import com.caminando.Caminando.businesslayer.services.dto.travel.TripDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.TripService;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public TripDTO createTrip(@RequestBody TripDTO tripDTO) {

        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        tripDTO.setUser(user);
        Trip savedTrip = tripService.save(tripDTO);
        return mapToDTO(savedTrip);
    }


    private TripDTO mapToDTO(Trip trip) {
        TripDTO tripDTO = new TripDTO();
        tripDTO.setTitle(trip.getTitle());
        tripDTO.setDescription(trip.getDescription());
        tripDTO.setLikes(trip.getLikes());
        tripDTO.setStartDate(trip.getStartDate());
        tripDTO.setEndDate(trip.getEndDate());
        tripDTO.setStatus(trip.getStatus());
        tripDTO.setPrivacy(trip.getPrivacy());
        tripDTO.setUser(trip.getUser());
        return tripDTO;
    }
}
