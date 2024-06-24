package com.caminando.Caminando.presentationlayer.api.controller.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.SuggestItineraryDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.ToDoDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.SuggestItineraryService;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.ToDoService;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.ToDo;
import com.caminando.Caminando.datalayer.entities.travel.Step;
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
@RequestMapping("/api/todo")
public class ToDoController {

    @Autowired
    private ToDoService service;

    @Autowired
    private SuggestItineraryService itService;

    @Autowired
    Mapper<SuggestItinerary, SuggestItineraryDTO> itMapper;

    @PostMapping("/api/{itineraryId}")
    public ResponseEntity<?> createToDO(@PathVariable Long itineraryId ,@RequestBody @Validated GenericModel model, BindingResult validator){
        if (validator.hasErrors()) {
            return ResponseEntity.badRequest().body(validator.getAllErrors());
        }

        try {
            ToDo createdToDo = service.saveToDo(itineraryId,model);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdToDo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable Long id) {
        ToDo toDo = service.getById(id);
        if (toDo!= null) {
            return new ResponseEntity<>(toDo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Page<ToDo>> getAllToDo(Pageable p){
        var allToDo = service.getAll(p);
        var headers = new HttpHeaders();
        headers.add("Todo:", String.valueOf(allToDo.getTotalElements()));
        return new ResponseEntity<>(allToDo, headers,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDo toDoModified){
        var toDo = service.update(id,toDoModified);
        return new ResponseEntity<>(toDo, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable Long id){
        var toDo = service.delete(id);
        return new ResponseEntity<>(toDo, HttpStatus.OK);
    }



}
