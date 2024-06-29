package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.SuggestItineraryDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.SuggestItineraryResponseDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class SuggestItineraryServiceImpl implements SuggestItineraryService {

    @Autowired
    private SuggestItineraryRepository suggestItineraryRepository;

    @Autowired
    private Mapper<SuggestItineraryDTO, SuggestItinerary> suggestItineraryDTOToEntityMapper;

    @Autowired
    private Mapper<SuggestItinerary, SuggestItineraryResponseDTO> suggestItineraryEntityToResponseMapper;

    @Autowired
    private EntityUtils utils;

    @Override
    public Page<SuggestItineraryResponseDTO> getAll(Pageable pageable) {
        Page<SuggestItinerary> suggestItineraries = suggestItineraryRepository.findAll(pageable);
        return suggestItineraries.map(suggestItineraryEntityToResponseMapper::map);
    }

    @Override
    public SuggestItineraryResponseDTO getById(Long id) {
        Optional<SuggestItinerary> suggestItinerary = suggestItineraryRepository.findById(id);
        return suggestItinerary.map(suggestItineraryEntityToResponseMapper::map).orElse(null);
    }

    @Override
    @Transactional
    public SuggestItineraryResponseDTO save(SuggestItineraryDTO dto) {
        SuggestItinerary entity = suggestItineraryDTOToEntityMapper.map(dto);
        SuggestItinerary savedEntity = suggestItineraryRepository.save(entity);
        return suggestItineraryEntityToResponseMapper.map(savedEntity);
    }

    @Override
    @Transactional
    public SuggestItineraryResponseDTO update(Long id, SuggestItineraryDTO dto) {
        Optional<SuggestItinerary> optionalEntity = suggestItineraryRepository.findById(id);
        if (optionalEntity.isPresent()) {
            SuggestItinerary existingEntity = optionalEntity.get();
            utils.copy(dto, existingEntity);
            SuggestItinerary updatedEntity = suggestItineraryRepository.save(existingEntity);
            return suggestItineraryEntityToResponseMapper.map(updatedEntity);
        }
        return null;
    }

    @Override
    @Transactional
    public SuggestItineraryResponseDTO delete(Long id) {
        Optional<SuggestItinerary> optionalEntity = suggestItineraryRepository.findById(id);
        if (optionalEntity.isPresent()) {
            SuggestItinerary entity = optionalEntity.get();
            suggestItineraryRepository.delete(entity);
            return suggestItineraryEntityToResponseMapper.map(entity);
        }
        return null;
    }
}
