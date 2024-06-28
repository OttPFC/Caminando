package com.caminando.Caminando.presentationlayer.api.controller.travel;

import com.caminando.Caminando.businesslayer.services.interfaces.travel.TripService;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import com.caminando.Caminando.presentationlayer.api.exceptions.ApiValidationException;
import com.caminando.Caminando.presentationlayer.api.models.travel.TripModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping()
    public ResponseEntity<Trip> createTrip(@RequestBody @Validated TripModel model, BindingResult validator) {
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }
        try {
            Trip trip = tripService.createTrip(model);
            return new ResponseEntity<>(trip, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        var trip = tripService.getById(id);
        return new ResponseEntity<>(trip, HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<Page<Trip>> getAllTrips(Pageable pageable) {
        var allTrip = tripService.getAll(pageable);
        var headers = new HttpHeaders();
        headers.add("Viaggi", String.valueOf(allTrip.getTotalElements()));
        return new ResponseEntity<>(allTrip, headers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody Trip tripModified) {

        var trip = tripService.update(id, tripModified);
        return new ResponseEntity<>(trip, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Trip> deleteTrip(@PathVariable Long id) {
        var trip = tripService.delete(id);
        return new ResponseEntity<>(trip, HttpStatus.OK);
    }

}