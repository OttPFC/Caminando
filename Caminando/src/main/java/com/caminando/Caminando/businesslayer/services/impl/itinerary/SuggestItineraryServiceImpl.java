package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.SuggestItineraryDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.SuggestItineraryService;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SuggestItineraryServiceImpl implements SuggestItineraryService {
    @Override
    public Page<SuggestItinerary> getAll(Pageable p) {
        return null;
    }

    @Override
    public SuggestItinerary getById(Long id) {
        return null;
    }

    @Override
    public SuggestItinerary save(SuggestItineraryDTO e) {
        return null;
    }

    @Override
    public SuggestItinerary update(Long id, SuggestItinerary e) {
        return null;
    }

    @Override
    public SuggestItinerary delete(Long id) {
        return null;
    }
}
