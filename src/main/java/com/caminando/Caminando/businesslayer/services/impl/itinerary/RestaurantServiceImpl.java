package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.RestaurantDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.RestaurantResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.RestaurantService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.Restaurant;
import com.caminando.Caminando.datalayer.repositories.itinerary.RestaurantRepository;
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
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository repo;

    @Autowired
    private Mapper<RestaurantDTO, Restaurant> mapper;

    @Autowired
    private Mapper<Restaurant, RestaurantResponseDTO> restaurantEntityToResponseMapper;

    @Autowired
    private EntityUtils utils;

    @Override
    public Page<RestaurantResponseDTO> getAll(Pageable pageable) {
        Page<Restaurant> restaurants = repo.findAll(pageable);
        return restaurants.map(restaurantEntityToResponseMapper::map);
    }

    @Override
    public RestaurantResponseDTO getById(Long id) {
        Optional<Restaurant> restaurant = repo.findById(id);
        return restaurant.map(restaurantEntityToResponseMapper::map).orElse(null);
    }

    @Override
    @Transactional
    public RestaurantResponseDTO save(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = mapper.map(restaurantDTO);
        Restaurant savedRestaurant = repo.save(restaurant);
        return restaurantEntityToResponseMapper.map(savedRestaurant);
    }

    @Override
    @Transactional
    public RestaurantResponseDTO update(Long id, RestaurantDTO restaurantDTO) {
        Optional<Restaurant> optionalRestaurant = repo.findById(id);
        if (optionalRestaurant.isPresent()) {
            Restaurant existingRestaurant = optionalRestaurant.get();
            utils.copy(restaurantDTO, existingRestaurant);
            Restaurant updatedRestaurant = repo.save(existingRestaurant);
            return restaurantEntityToResponseMapper.map(updatedRestaurant);
        }
        return null;
    }

    @Override
    @Transactional
    public RestaurantResponseDTO delete(Long id) {
        Optional<Restaurant> optionalRestaurant = repo.findById(id);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            repo.delete(restaurant);
            return restaurantEntityToResponseMapper.map(restaurant);
        }
        return null;
    }
}
