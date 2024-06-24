package com.caminando.Caminando.presentationlayer.api.controller.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.QuickFactsDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.QuickFactService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.QuickFacts;
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
@RequestMapping("/api/quick-fact")
public class QuickFactsController {

    @Autowired
    private QuickFactService service;



    @PostMapping
    public ResponseEntity<QuickFacts> createQuickFacts(@RequestBody @Validated GenericModel model, BindingResult validator){
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }
        var quickFacts = service.save(QuickFactsDTO.builder()
                .withTitle(model.title())
                .withDescription(model.description())

                .build());
        return new ResponseEntity<>(quickFacts, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuickFacts> getQuickFactsById(@PathVariable Long id) {
        QuickFacts quickFacts = service.getById(id);
        if (quickFacts!= null) {
            return new ResponseEntity<>(quickFacts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Page<QuickFacts>> getAllQuickFacts(Pageable p){
        var allquickFacts = service.getAll(p);
        var headers = new HttpHeaders();
        headers.add("Todo:", String.valueOf(allquickFacts.getTotalElements()));
        return new ResponseEntity<>(allquickFacts, headers,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuickFacts> updateRestaurant(@PathVariable Long id, @RequestBody QuickFacts quickFactsModified){
        var quickFacts = service.update(id,quickFactsModified);
        return new ResponseEntity<>(quickFacts, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<QuickFacts> deleteQuickFacts(@PathVariable Long id){
        var quickFacts = service.delete(id);
        return new ResponseEntity<>(quickFacts, HttpStatus.OK);
    }
}
