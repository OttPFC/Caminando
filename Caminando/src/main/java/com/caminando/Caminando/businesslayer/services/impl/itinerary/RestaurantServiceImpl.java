package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.RestaurantDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.RestaurantService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {
    @Override
    public Page<Restaurant> getAll(Pageable p) {
        return null;
    }

    @Override
    public Restaurant getById(Long id) {
        return null;
    }

    @Override
    public Restaurant save(RestaurantDTO e) {
        return null;
    }

    @Override
    public Restaurant update(Long id, Restaurant e) {
        return null;
    }

    @Override
    public Restaurant delete(Long id) {
        return null;
    }
}
