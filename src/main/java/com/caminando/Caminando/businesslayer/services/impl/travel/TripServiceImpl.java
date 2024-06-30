package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.TripRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.TripResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.TripService;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.travel.TripRepository;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import com.caminando.Caminando.presentationlayer.api.exceptions.NotFoundException;
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
    private Mapper<TripRequestDTO, Trip> mapTripDTOToEntity;

    @Autowired
    private Mapper<Trip, TripResponseDTO> tripEntityToResponseMapper;

    @Autowired
    private Mapper<User, RegisteredUserDTO> userEntityToUserDTOMapper;

    @Autowired
    private EntityUtils utils;

    @Override
    public Page<TripResponseDTO> getAll(Pageable pageable) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Page<Trip> trips = tripRepository.findAllByUserId(user.getId(), pageable);
        return trips.map(tripEntityToResponseMapper::map);
    }

    @Override
    public TripResponseDTO getById(Long id) {
        Optional<Trip> trip = tripRepository.findById(id);
        return trip.map(tripEntityToResponseMapper::map).orElse(null);
    }

    @Override
    @Transactional
    public TripResponseDTO save(TripRequestDTO tripRequestDTO) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        Trip trip = mapTripDTOToEntity.map(tripRequestDTO);
        trip.setUser(user);
        Trip savedTrip = tripRepository.save(trip);

        return tripEntityToResponseMapper.map(savedTrip);
    }

    @Override
    @Transactional
    public TripResponseDTO update(Long id, TripRequestDTO tripRequestDTO) {
        Trip existingTrip = tripRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        utils.copy(tripRequestDTO, existingTrip);
        Trip updatedTrip = tripRepository.save(existingTrip);
        return tripEntityToResponseMapper.map(updatedTrip);
    }

    @Override
    @Transactional
    public TripResponseDTO delete(Long id) {
        Trip trip = tripRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        tripRepository.delete(trip);
        return tripEntityToResponseMapper.map(trip);
    }

    @Override
    public Trip getTripByIdAndUserId(Long tripId, Long userId) {
        return tripRepository.findByIdAndUserId(tripId, userId).orElse(null);
    }
}
