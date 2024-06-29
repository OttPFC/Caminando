package com.caminando.Caminando.presentationlayer.api.controller.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.ToDoDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.ToDoResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @GetMapping
    public ResponseEntity<Page<ToDoResponseDTO>> getAllToDos(Pageable pageable) {
        Page<ToDoResponseDTO> todos = toDoService.getAll(pageable);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoResponseDTO> getToDoById(@PathVariable Long id) {
        ToDoResponseDTO todo = toDoService.getById(id);
        if (todo != null) {
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ToDoResponseDTO> createToDo(@RequestBody ToDoDTO toDoDTO) {
        ToDoResponseDTO createdToDo = toDoService.save(toDoDTO);
        return new ResponseEntity<>(createdToDo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDoResponseDTO> updateToDo(@PathVariable Long id, @RequestBody ToDoDTO toDoDTO) {
        ToDoResponseDTO updatedToDo = toDoService.update(id, toDoDTO);
        if (updatedToDo != null) {
            return new ResponseEntity<>(updatedToDo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToDo(@PathVariable Long id) {
        ToDoResponseDTO deletedToDo = toDoService.delete(id);
        if (deletedToDo != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
