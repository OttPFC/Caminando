package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.TripRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.TripResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.TripService;
import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.travel.Step;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.travel.TripRepository;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import com.caminando.Caminando.presentationlayer.api.exceptions.NotFoundException;
import com.caminando.Caminando.presentationlayer.utility.EntityUtils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Mapper<ImageDTO, Image> mapImageDTOToEntity;
    @Autowired
    private Mapper<ImageResponseDTO, Image> responseToEntity;
    @Autowired
    private Mapper<TripRequestDTO, Trip> mapTripDTOToEntity;

    @Autowired
    private Mapper<Trip, TripResponseDTO> tripEntityToResponseMapper;

    @Autowired
    private Mapper<User, RegisteredUserDTO> userEntityToUserDTOMapper;
    @Value("${CLOUDINARY_URL}")
    private String cloudinaryUrl;
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
        log.info("Updating trip with id: {}", id);
        Trip existingTrip = tripRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        // Recupera l'utente attuale
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verifica se l'utente attuale è il proprietario del trip
        if (!existingTrip.getUser().getId().equals(user.getId())) {
            log.warn("User {} not authorized to update trip {}", username, id);
            throw new RuntimeException("User not authorized to update this trip");
        }

        // Mappa i valori dal TripRequestDTO all'entità Trip esistente
        existingTrip.setTitle(tripRequestDTO.getTitle());
        existingTrip.setDescription(tripRequestDTO.getDescription());
        existingTrip.setStartDate(tripRequestDTO.getStartDate());
        existingTrip.setEndDate(tripRequestDTO.getEndDate());
        existingTrip.setLikes(tripRequestDTO.getLikes());
        existingTrip.setStatus(tripRequestDTO.getStatus());
        existingTrip.setPrivacy(tripRequestDTO.getPrivacy());

        // Se ci sono delle steps, mappa e aggiungile al trip
        if (tripRequestDTO.getSteps() != null) {
            existingTrip.getSteps().clear();
            tripRequestDTO.getSteps().forEach(stepDTO -> {
                Step step = new Step(); // supponiamo che ci sia un mapper o costruttore adeguato per creare Step da StepDTO
                // mappa i campi dello stepDTO allo step
                existingTrip.addStep(step);
            });
        }

        // Se c'è un'immagine di copertina aggiornata, mappala e impostala
        if (tripRequestDTO.getCoverImage() != null) {
            log.info("Updating cover image for trip {}", id);
            Image coverImage = responseToEntity.map(tripRequestDTO.getCoverImage());
            existingTrip.setCoverImage(coverImage);
        }

        try {
            // Salva le modifiche
            Trip updatedTrip = tripRepository.save(existingTrip);

            // Restituisci il TripResponseDTO mappato dall'entità aggiornata
            return tripEntityToResponseMapper.map(updatedTrip);
        } catch (Exception e) {
            log.error("Error updating trip with id: {}", id, e);
            throw new RuntimeException("Failed to update trip", e);
        }
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

    @Override
    @Transactional
    public TripResponseDTO saveImage(Long id, MultipartFile file) throws IOException {
        log.info("Saving image for trip with id: {}", id);
        Trip trip = tripRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
        var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = (String) uploadResult.get("url");

        ImageDTO image = new ImageDTO();
        image.setImageURL(imageUrl);
        trip.setCoverImage(mapImageDTOToEntity.map(image));

        tripRepository.save(trip);
        log.info("Image saved for trip with id: {}", id);
        return tripEntityToResponseMapper.map(trip);
    }

}
