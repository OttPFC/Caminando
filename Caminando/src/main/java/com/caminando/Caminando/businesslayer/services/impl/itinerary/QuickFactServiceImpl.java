package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.QuickFactsDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.RestaurantDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.SuggestItineraryDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.QuickFactService;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.QuickFacts;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.Restaurant;
import com.caminando.Caminando.datalayer.repositories.itinerary.QuickFatcsRepository;
import com.caminando.Caminando.datalayer.repositories.itinerary.SuggestItineraryRepository;
import com.caminando.Caminando.presentationlayer.api.models.itinerary.GenericModel;
import com.caminando.Caminando.presentationlayer.utility.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class QuickFactServiceImpl implements QuickFactService {

    @Autowired
    QuickFatcsRepository repo;
    @Autowired
    private SuggestItineraryRepository itRepo;

    @Autowired
    private Mapper<SuggestItinerary, SuggestItineraryDTO> itineraryToDTO;
    @Autowired
    Mapper<QuickFactsDTO, QuickFacts> mapper;

    @Autowired
    EntityUtils utils;

    @Override
    public Page<QuickFacts> getAll(Pageable p) {
        return repo.findAll(p);
    }

    @Override
    public QuickFacts getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public QuickFacts save(QuickFactsDTO e) {
        return repo.save(mapper.map(e));
    }

    @Override
    public QuickFacts update(Long id, QuickFacts e) {
        var entity = this.getById(id);
        utils.copy(e, entity);
        return repo.save(entity);
    }

    @Override
    public QuickFacts delete(Long id) {
        Optional<QuickFacts> optionalEntity = repo.findById(id);
        if (optionalEntity.isPresent()) {
            QuickFacts entity = optionalEntity.get();
            repo.delete(entity);
            return entity;
        }
        return null;
    }

    @Override
    public QuickFacts saveQuickFact(Long itineraryId, GenericModel model) {

            SuggestItinerary itinerary = itRepo.findById(itineraryId).orElse(null);

            SuggestItineraryDTO dto = itineraryToDTO.map(itinerary);

        QuickFactsDTO quickFactsDto = QuickFactsDTO.builder()
                    .withTitle(model.title())
                    .withDescription(model.description())
                    .withSuggestItinerary(dto)
                    .build();

        QuickFacts newQuickFacts = repo.save(mapper.map(quickFactsDto));
            itinerary.getQuickFacts().add(newQuickFacts);
            return newQuickFacts;
        }
    }

