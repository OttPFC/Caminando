package com.caminando.Caminando.presentationlayer.api.controller.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.PlaceToStayDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.PlaceToStayResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.PlaceToStayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/placesToStay")
public class PlaceToStayController {

    @Autowired
    private PlaceToStayService placeToStayService;

    @GetMapping
    public ResponseEntity<Page<PlaceToStayResponseDTO>> getAllPlacesToStay(Pageable pageable) {
        Page<PlaceToStayResponseDTO> placesToStay = placeToStayService.getAll(pageable);
        return new ResponseEntity<>(placesToStay, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceToStayResponseDTO> getPlaceToStayById(@PathVariable Long id) {
        PlaceToStayResponseDTO placeToStay = placeToStayService.getById(id);
        if (placeToStay != null) {
            return new ResponseEntity<>(placeToStay, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PlaceToStayResponseDTO> createPlaceToStay(@RequestBody PlaceToStayDTO placeToStayDTO) {
        PlaceToStayResponseDTO createdPlaceToStay = placeToStayService.save(placeToStayDTO);
        return new ResponseEntity<>(createdPlaceToStay, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceToStayResponseDTO> updatePlaceToStay(@PathVariable Long id, @RequestBody PlaceToStayDTO placeToStayDTO) {
        PlaceToStayResponseDTO updatedPlaceToStay = placeToStayService.update(id, placeToStayDTO);
        if (updatedPlaceToStay != null) {
            return new ResponseEntity<>(updatedPlaceToStay, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaceToStay(@PathVariable Long id) {
        PlaceToStayResponseDTO deletedPlaceToStay = placeToStayService.delete(id);
        if (deletedPlaceToStay != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
