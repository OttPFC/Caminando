package com.caminando.Caminando.presentationlayer.api.controller.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.RestaurantDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.ToDoDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.RestaurantService;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.ToDoService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.Restaurant;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.ToDo;
import com.caminando.Caminando.presentationlayer.api.exceptions.ApiValidationException;
import com.caminando.Caminando.presentationlayer.api.models.itinerary.GenericModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService service;



    @PostMapping("/api/{itineraryId}")
    public ResponseEntity<?> createRestaurant(@PathVariable Long itineraryId ,@RequestBody @Validated GenericModel model, BindingResult validator){
        if (validator.hasErrors()) {
            return ResponseEntity.badRequest().body(validator.getAllErrors());
        }
        try {
            Restaurant createdRestaurant = service.saveRestaurant(itineraryId,model);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = service.getById(id);
        if (restaurant!= null) {
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Restaurant>> getAllRestaurant(Pageable p){
        var allRestaurant = service.getAll(p);
        var headers = new HttpHeaders();
        headers.add("Todo:", String.valueOf(allRestaurant.getTotalElements()));
        return new ResponseEntity<>(allRestaurant, headers,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurantModified){
        var restaurant = service.update(id,restaurantModified);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable Long id){
        var restaurant = service.delete(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
