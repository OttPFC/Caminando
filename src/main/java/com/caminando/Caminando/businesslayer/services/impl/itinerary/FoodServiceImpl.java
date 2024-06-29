package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.FoodDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.FoodResponseDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private Mapper<FoodDTO, Food> foodMapper;

    @Autowired
    private Mapper<Food, FoodResponseDTO> foodEntityToResponseMapper;

    @Autowired
    private EntityUtils utils;

    @Override
    public Page<FoodResponseDTO> getAll(Pageable pageable) {
        Page<Food> foods = foodRepository.findAll(pageable);
        return foods.map(foodEntityToResponseMapper::map);
    }

    @Override
    public FoodResponseDTO getById(Long id) {
        Optional<Food> food = foodRepository.findById(id);
        return food.map(foodEntityToResponseMapper::map).orElse(null);
    }

    @Override
    @Transactional
    public FoodResponseDTO save(FoodDTO foodDTO) {
        Food food = foodMapper.map(foodDTO);
        Food savedFood = foodRepository.save(food);
        return foodEntityToResponseMapper.map(savedFood);
    }

    @Override
    @Transactional
    public FoodResponseDTO update(Long id, FoodDTO foodDTO) {
        Optional<Food> optionalFood = foodRepository.findById(id);
        if (optionalFood.isPresent()) {
            Food existingFood = optionalFood.get();
            utils.copy(foodDTO, existingFood);
            Food updatedFood = foodRepository.save(existingFood);
            return foodEntityToResponseMapper.map(updatedFood);
        }
        return null;
    }

    @Override
    @Transactional
    public FoodResponseDTO delete(Long id) {
        Optional<Food> optionalFood = foodRepository.findById(id);
        if (optionalFood.isPresent()) {
            Food food = optionalFood.get();
            foodRepository.delete(food);
            return foodEntityToResponseMapper.map(food);
        }
        return null;
    }
}
