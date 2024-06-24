package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.FoodDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.FoodService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.Food;
import com.caminando.Caminando.datalayer.repositories.itinerary.FoodRepository;
import com.caminando.Caminando.presentationlayer.utility.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    Mapper<FoodDTO, Food> foodMapper;

    @Autowired
    EntityUtils utils;



    @Override
    public Page<Food> getAll(Pageable p) {
        return foodRepository.findAll(p);
    }

    @Override
    public Food getById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }

    @Override
    public Food save(FoodDTO e) {
        return foodRepository.save(foodMapper.map(e));
    }

    @Override
    public Food update(Long id, Food e) {
        var entity = this.getById(id);
        utils.copy(e, entity);
        return foodRepository.save(entity);
    }

    @Override
    public Food delete(Long id) {
        Optional<Food> optinalEntity = foodRepository.findById(id);
        if (optinalEntity.isPresent()) {
            Food entity = optinalEntity.get();
            foodRepository.delete(entity);
            return entity;
        }
        return null;
    }
}
