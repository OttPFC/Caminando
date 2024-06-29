package com.caminando.Caminando.presentationlayer.api.controller.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.QuickFactsDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.QuickFactsResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.QuickFactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quickfacts")
public class QuickFactsController {

    @Autowired
    private QuickFactService quickFactService;

    @GetMapping
    public ResponseEntity<Page<QuickFactsResponseDTO>> getAllQuickFacts(Pageable pageable) {
        Page<QuickFactsResponseDTO> quickFacts = quickFactService.getAll(pageable);
        return new ResponseEntity<>(quickFacts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuickFactsResponseDTO> getQuickFactById(@PathVariable Long id) {
        QuickFactsResponseDTO quickFact = quickFactService.getById(id);
        if (quickFact != null) {
            return new ResponseEntity<>(quickFact, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<QuickFactsResponseDTO> createQuickFact(@RequestBody QuickFactsDTO quickFactsDTO) {
        QuickFactsResponseDTO createdQuickFact = quickFactService.save(quickFactsDTO);
        return new ResponseEntity<>(createdQuickFact, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuickFactsResponseDTO> updateQuickFact(@PathVariable Long id, @RequestBody QuickFactsDTO quickFactsDTO) {
        QuickFactsResponseDTO updatedQuickFact = quickFactService.update(id, quickFactsDTO);
        if (updatedQuickFact != null) {
            return new ResponseEntity<>(updatedQuickFact, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuickFact(@PathVariable Long id) {
        QuickFactsResponseDTO deletedQuickFact = quickFactService.delete(id);
        if (deletedQuickFact != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
