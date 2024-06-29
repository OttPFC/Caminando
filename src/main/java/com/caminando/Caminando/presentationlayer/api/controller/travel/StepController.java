package com.caminando.Caminando.presentationlayer.api.controller.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.StepRequestDTO;
import com.caminando.Caminando.businesslayer.services.dto.travel.StepResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.StepService;
import com.caminando.Caminando.presentationlayer.api.models.travel.StepModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/steps")
public class StepController {

    @Autowired
    private StepService stepService;

    @GetMapping
    public ResponseEntity<Page<StepResponseDTO>> getAllSteps(Pageable pageable) {
        Page<StepResponseDTO> steps = stepService.getAll(pageable);
        return ResponseEntity.ok(steps);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StepResponseDTO> getStepById(@PathVariable Long id) {
        StepResponseDTO step = stepService.getById(id);
        if (step != null) {
            return ResponseEntity.ok(step);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("create")
    public ResponseEntity<StepResponseDTO> createStep(@RequestBody StepModel model, @RequestParam Long tripId, @RequestParam Long positionId) {
        var stepRequest = StepRequestDTO.builder()
                .withDescription(model.description())
                .withArrivalDate(model.arrivalDate())
                .withDepartureDate(model.departureDate())
                .build();
        StepResponseDTO createdStep = stepService.createStep(stepRequest, tripId, positionId);
        return ResponseEntity.ok(createdStep);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StepResponseDTO> updateStep(@PathVariable Long id, @RequestBody StepRequestDTO stepRequestDTO) {
        StepResponseDTO updatedStep = stepService.update(id, stepRequestDTO);
        if (updatedStep != null) {
            return ResponseEntity.ok(updatedStep);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StepResponseDTO> deleteStep(@PathVariable Long id) {
        StepResponseDTO deletedStep = stepService.delete(id);
        if (deletedStep != null) {
            return ResponseEntity.ok(deletedStep);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
