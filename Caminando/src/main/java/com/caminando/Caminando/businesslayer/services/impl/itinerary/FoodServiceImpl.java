package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.FoodDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.FoodService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.Food;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FoodServiceImpl implements FoodService {
    @Override
    public Page<Food> getAll(Pageable p) {
        return null;
    }

    @Override
    public Food getById(Long id) {
        return null;
    }

    @Override
    public Food save(FoodDTO e) {
        return null;
    }

    @Override
    public Food update(Long id, Food e) {
        return null;
    }

    @Override
    public Food delete(Long id) {
        return null;
    }
}
