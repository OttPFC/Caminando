package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.ToDoDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.ToDoResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.SuggestItineraryDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.ToDoService;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.ToDo;
import com.caminando.Caminando.datalayer.repositories.itinerary.ToDoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class ToDoServiceImpl implements ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private Mapper<ToDoDTO, ToDo> toDoDTOToEntityMapper;

    @Autowired
    private Mapper<ToDo, ToDoResponseDTO> toDoEntityToResponseMapper;

    @Autowired
    private Mapper<SuggestItineraryDTO, SuggestItinerary> suggestItineraryDTOToEntityMapper;

    @Override
    public Page<ToDoResponseDTO> getAll(Pageable pageable) {
        Page<ToDo> todos = toDoRepository.findAll(pageable);
        return todos.map(toDoEntityToResponseMapper::map);
    }

    @Override
    public ToDoResponseDTO getById(Long id) {
        Optional<ToDo> todo = toDoRepository.findById(id);
        return todo.map(toDoEntityToResponseMapper::map).orElse(null);
    }

    @Override
    @Transactional
    public ToDoResponseDTO save(ToDoDTO toDoDTO) {
        ToDo todo = toDoDTOToEntityMapper.map(toDoDTO);
        if (toDoDTO.getSuggestItinerary() != null) {
            SuggestItinerary suggestItinerary = suggestItineraryDTOToEntityMapper.map(toDoDTO.getSuggestItinerary());
            todo.setSuggestItinerary(suggestItinerary);
        }
        ToDo savedTodo = toDoRepository.save(todo);
        return toDoEntityToResponseMapper.map(savedTodo);
    }

    @Override
    @Transactional
    public ToDoResponseDTO update(Long id, ToDoDTO toDoDTO) {
        Optional<ToDo> optionalToDo = toDoRepository.findById(id);
        if (optionalToDo.isPresent()) {
            ToDo existingToDo = optionalToDo.get();
            // Aggiorna i campi dell'entità con i valori del DTO
            existingToDo.setTitle(toDoDTO.getTitle());
            existingToDo.setDescription(toDoDTO.getDescription());
            if (toDoDTO.getSuggestItinerary() != null) {
                SuggestItinerary suggestItinerary = suggestItineraryDTOToEntityMapper.map(toDoDTO.getSuggestItinerary());
                existingToDo.setSuggestItinerary(suggestItinerary);
            }
            ToDo updatedToDo = toDoRepository.save(existingToDo);
            return toDoEntityToResponseMapper.map(updatedToDo);
        }
        return null;
    }

    @Override
    @Transactional
    public ToDoResponseDTO delete(Long id) {
        Optional<ToDo> optionalToDo = toDoRepository.findById(id);
        if (optionalToDo.isPresent()) {
            ToDo todo = optionalToDo.get();
            toDoRepository.delete(todo);
            return toDoEntityToResponseMapper.map(todo);
        }
        return null;
    }
}
