package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.TripDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.TripService;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.travel.TripRepository;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import com.caminando.Caminando.presentationlayer.utility.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper<TripDTO, Trip> tripDTOToEntityMapper;

    @Autowired
    private Mapper<Trip, TripDTO> tripEntityToDTOMapper;

    @Autowired
    EntityUtils utils;

    @Override
    public Page<Trip> getAll(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    @Override
    public Trip getById(Long id) {
        Optional<Trip> trip = tripRepository.findById(id);
        return trip.orElse(null);
    }

    @Override
    @Transactional
    public Trip save(TripDTO tripDTO) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        Trip trip = tripDTOToEntityMapper.map(tripDTO);
        trip.setUser(user);

        return tripRepository.save(trip);
    }

    @Override
    @Transactional
    public Trip update(Long id, Trip tripDetails) {
        Optional<Trip> optionalTrip = tripRepository.findById(id);
        if (optionalTrip.isPresent()) {
            Trip existingTrip = optionalTrip.get();
            utils.copy(tripDetails, existingTrip);
            return tripRepository.save(existingTrip);
        }
        return null;
    }

    @Override
    @Transactional
    public Trip delete(Long id) {
        Optional<Trip> optionalTrip = tripRepository.findById(id);
        if (optionalTrip.isPresent()) {
            Trip trip = optionalTrip.get();
            tripRepository.delete(trip);
            return trip;
        }
        return null;
    }

    @Override
    public TripDTO mapEntityToDTO(Trip trip) {
        return tripEntityToDTOMapper.map(trip);
    }

    @Override
    public Trip getTripByIdAndUserId(Long tripId, Long userId) {
        return tripRepository.findByIdAndUserId(tripId, userId).orElse(null);
    }
}
