package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.RestaurantDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.SuggestItineraryDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.ToDoDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.RestaurantService;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.Restaurant;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.ToDo;
import com.caminando.Caminando.datalayer.repositories.itinerary.RestaurantRepository;
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
public class RestaurantServiceImpl implements RestaurantService {


    @Autowired
    RestaurantRepository repo;
    @Autowired
    private SuggestItineraryRepository itRepo;

    @Autowired
    private Mapper<SuggestItinerary, SuggestItineraryDTO> itineraryToDTO;
    @Autowired
    Mapper<RestaurantDTO,Restaurant> mapper;

    @Autowired
    EntityUtils utils;


    @Override
    public Page<Restaurant> getAll(Pageable p) {
        return repo.findAll(p);
    }

    @Override
    public Restaurant getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Restaurant save(RestaurantDTO e) {
        return repo.save(mapper.map(e));
    }

    @Override
    public Restaurant update(Long id, Restaurant e) {
        var entity = this.getById(id);
        utils.copy(e,entity);
        return repo.save(entity);
    }

    @Override
    public Restaurant delete(Long id) {
        Optional<Restaurant> optionalEntity = repo.findById(id);
        if (optionalEntity.isPresent()) {
            Restaurant entity = optionalEntity.get();
            repo.delete(entity);
            return entity;
        }
        return null;
    }



    @Override
    public Restaurant saveRestaurant(Long itineraryId, GenericModel model) {

        SuggestItinerary itinerary = itRepo.findById(itineraryId).orElse(null);

        SuggestItineraryDTO dto = itineraryToDTO.map(itinerary);

        RestaurantDTO restaurantDto = RestaurantDTO.builder()
                .withTitle(model.title())
                .withDescription(model.description())
                .withSuggestItinerary(dto)
                .build();

        Restaurant newRestaurant = repo.save(mapper.map(restaurantDto));
        itinerary.getRestaurant().add(newRestaurant);
        return newRestaurant;
    }
}
