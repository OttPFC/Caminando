package com.caminando.Caminando.presentationlayer.api.controller.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.FoodDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.FoodService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.Food;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.PlaceToStay;
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
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService service;



    @PostMapping("/api/{itineraryId}")
    public ResponseEntity<?> createFood(@PathVariable Long itineraryId ,@RequestBody @Validated GenericModel model, BindingResult validator){
        if (validator.hasErrors()) {
            return ResponseEntity.badRequest().body(validator.getAllErrors());
        }
        try {
            Food createdFood = service.saveFood(itineraryId,model);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFood);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id) {
        Food food = service.getById(id);
        if (food!= null) {
            return new ResponseEntity<>(food, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Food>> getAllFood(Pageable p){
        var allFood = service.getAll(p);
        var headers = new HttpHeaders();
        headers.add("Todo:", String.valueOf(allFood.getTotalElements()));
        return new ResponseEntity<>(allFood, headers,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @RequestBody Food foodModified){
        var food = service.update(id,foodModified);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Food> deleteFood(@PathVariable Long id){
        var food = service.delete(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
