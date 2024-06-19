package com.caminando.Caminando.presentationlayer.api.controller.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.PositionDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.PositionService;
import com.caminando.Caminando.datalayer.entities.travel.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @PostMapping
    public ResponseEntity<PositionDTO> savePosition(@RequestBody PositionDTO positionDTO) {
        positionDTO.setTimestamp(Instant.now());
        Position savedPosition = positionService.save(positionDTO);
        PositionDTO position = positionService.mapEntityToDTO(savedPosition);
        return new ResponseEntity<>(position, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<PositionDTO> getNomeLocalitaAndTimestampById(@PathVariable Long id) {
        try {
            PositionDTO positionDTO = positionService.getNomeLocalitaAndTimestampById(id);
            return new ResponseEntity<>(positionDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Position>> getAllPositions(Pageable pageable) {
        Page<Position> positions = positionService.getAll(pageable);
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable Long id) {
        Position position = positionService.getById(id);
        if (position != null) {
            return new ResponseEntity<>(position, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable Long id, @RequestBody Position positionDetails) {
        Position updatedPosition = positionService.update(id, positionDetails);
        if (updatedPosition != null) {
            return new ResponseEntity<>(updatedPosition, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Position> deletePosition(@PathVariable Long id) {
        Position deletedPosition = positionService.delete(id);
        if (deletedPosition != null) {
            return new ResponseEntity<>(deletedPosition, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
