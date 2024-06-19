package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.SuggestItineraryDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.SuggestItineraryService;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.datalayer.repositories.itinerary.SuggestItineraryRepository;
import com.caminando.Caminando.presentationlayer.utility.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SuggestItineraryServiceImpl implements SuggestItineraryService {



    @Autowired
    SuggestItineraryRepository suggestItineraryRepository;
    @Autowired
    Mapper<SuggestItineraryDTO, SuggestItinerary> suggestItineraryDTOToEntityMapper;

    @Autowired
    Mapper<SuggestItinerary, SuggestItineraryDTO> suggestItineraryEntityToDTOMapper;


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
}
