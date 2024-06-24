package com.caminando.Caminando.presentationlayer.api.controller.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.PlaceToStayDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.PlaceToStayService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.PlaceToStay;
import com.caminando.Caminando.presentationlayer.api.exceptions.ApiValidationException;
import com.caminando.Caminando.presentationlayer.api.models.itinerary.GenericModel;
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
@RequestMapping("/api/place-to-stay")
public class PlaceToStayController {
    @Autowired
    private PlaceToStayService service;



    @PostMapping
    public ResponseEntity<PlaceToStay> createPlaceToStay(@RequestBody @Validated GenericModel model, BindingResult validator){
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }
        var placeToStay = service.save(PlaceToStayDTO.builder()
                .withTitle(model.title())
                .withDescription(model.description())

                .build());
        return new ResponseEntity<>(placeToStay, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceToStay> getPlaceToStayById(@PathVariable Long id) {
        PlaceToStay placeToStay = service.getById(id);
        if (placeToStay!= null) {
            return new ResponseEntity<>(placeToStay, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Page<PlaceToStay>> getAllPlaceToStay(Pageable p){
        var allplaceToStay = service.getAll(p);
        var headers = new HttpHeaders();
        headers.add("Todo:", String.valueOf(allplaceToStay.getTotalElements()));
        return new ResponseEntity<>(allplaceToStay, headers,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceToStay> updatePlaceToStay(@PathVariable Long id, @RequestBody PlaceToStay placeToStayModified){
        var placeToStay = service.update(id,placeToStayModified);
        return new ResponseEntity<>(placeToStay, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<PlaceToStay> deleteQuickFacts(@PathVariable Long id){
        var placeToStay = service.delete(id);
        return new ResponseEntity<>(placeToStay, HttpStatus.OK);
    }

}
