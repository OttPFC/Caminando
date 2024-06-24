package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.SuggestItineraryDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.SuggestItineraryService;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.datalayer.entities.travel.User;
import com.caminando.Caminando.datalayer.repositories.UserRepository;
import com.caminando.Caminando.datalayer.repositories.itinerary.SuggestItineraryRepository;
import com.caminando.Caminando.presentationlayer.api.models.itinerary.SuggestItineraryModel;
import com.caminando.Caminando.presentationlayer.utility.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SuggestItineraryServiceImpl implements SuggestItineraryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    SuggestItineraryRepository suggestItineraryRepository;
    @Autowired
    Mapper<SuggestItineraryDTO, SuggestItinerary> suggestItineraryDTOToEntityMapper;

    @Autowired
    Mapper<SuggestItinerary, SuggestItineraryDTO> suggestItineraryEntityToDTOMapper;

    @Autowired
    private Mapper<User, RegisteredUserDTO> userEntityToUserDTOMapper;
    @Autowired
    EntityUtils utils;

    @Override
    public Page<SuggestItinerary> getAll(Pageable pageable) {
        return suggestItineraryRepository.findAll(pageable);
    }

    @Override
    public SuggestItinerary getById(Long id) {
        return suggestItineraryRepository.findById(id).orElse(null);
    }

    @Override
    public SuggestItinerary save(SuggestItineraryDTO dto) {
        SuggestItinerary entity = suggestItineraryDTOToEntityMapper.map(dto);
        return suggestItineraryRepository.save(entity);
    }

    @Override
    public SuggestItinerary update(Long id, SuggestItinerary s) {
        var sug = this.getById(id);
        utils.copy(s, sug);
        return suggestItineraryRepository.save(sug);
    }

    @Override
    public SuggestItinerary delete(Long id) {
        Optional<SuggestItinerary> optionalEntity = suggestItineraryRepository.findById(id);
        if (optionalEntity.isPresent()) {
            SuggestItinerary entity = optionalEntity.get();
            suggestItineraryRepository.delete(entity);
            return entity;
        }
        return null;
    }

    @Autowired
    Mapper<SuggestItineraryDTO, SuggestItinerary> itineraryToDTO;

    @Override
    public SuggestItinerary saveItinerary(SuggestItineraryModel model) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        RegisteredUserDTO userDTO = userEntityToUserDTOMapper.map(user);

        SuggestItineraryDTO itineraryDTO = SuggestItineraryDTO.builder()
                .withName(model.name())
                .withDescription(model.description())
                .withLocation(model.location())
                .withUser(userDTO)
                .build();

        SuggestItinerary newItinerary = suggestItineraryRepository.save(itineraryToDTO.map(itineraryDTO));
        return newItinerary;

    }
}
