package com.caminando.Caminando.presentationlayer.api.controller.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.PositionRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.PositionResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.PositionService;
import com.caminando.Caminando.presentationlayer.api.models.travel.PositionModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @PostMapping
    public ResponseEntity<?> savePosition(@RequestBody @Validated PositionModel model, BindingResult validator) {
        if (validator.hasErrors()) {
            return ResponseEntity.badRequest().body(validator.getAllErrors());
        }
        try {
            PositionRequestDTO positionRequestDTO = PositionRequestDTO.builder()
                    .withLatitude(model.latitude())
                    .withLongitude(model.longitude())
                    .withTimestamp(model.timestamp())
                    .withNomeLocalita(model.nomeLocalita())
                    .build();
            PositionResponseDTO createdPosition = positionService.savePosition(positionRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPosition);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<PositionRequestDTO> getNomeLocalitaAndTimestampById(@PathVariable Long id) {
        try {
            PositionRequestDTO positionRequestDTO = positionService.getNomeLocalitaAndTimestampById(id);
            return new ResponseEntity<>(positionRequestDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Page<PositionResponseDTO>> getAllPositions(Pageable pageable) {
        Page<PositionResponseDTO> positions = positionService.getAll(pageable);
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionResponseDTO> getPositionById(@PathVariable Long id) {
        PositionResponseDTO position = positionService.getById(id);
        if (position != null) {
            return new ResponseEntity<>(position, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PositionResponseDTO> updatePosition(@PathVariable Long id, @RequestBody PositionRequestDTO positionRequestDTO) {
        PositionResponseDTO updatedPosition = positionService.update(id, positionRequestDTO);
        if (updatedPosition != null) {
            return new ResponseEntity<>(updatedPosition, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PositionResponseDTO> deletePosition(@PathVariable Long id) {
        PositionResponseDTO deletedPosition = positionService.delete(id);
        if (deletedPosition != null) {
            return new ResponseEntity<>(deletedPosition, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
