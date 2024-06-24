package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.SuggestItineraryDTO;
import com.caminando.Caminando.businesslayer.services.dto.itinerary.ToDoDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.SuggestItineraryService;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.ToDoService;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.ToDo;
import com.caminando.Caminando.datalayer.repositories.itinerary.SuggestItineraryRepository;
import com.caminando.Caminando.datalayer.repositories.itinerary.ToDoRepository;
import com.caminando.Caminando.presentationlayer.api.models.itinerary.GenericModel;
import com.caminando.Caminando.presentationlayer.utility.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ToDoServiceImpl implements ToDoService {

    @Autowired
    ToDoRepository toDoRepository;
    @Autowired
    private SuggestItineraryRepository itRepo;

    @Autowired
    private Mapper<SuggestItinerary, SuggestItineraryDTO> itineraryToDTO;
    @Autowired
    Mapper<ToDoDTO,ToDo> toDoMapper;

    @Autowired
    EntityUtils utils;

    @Override
    public Page<ToDo> getAll(Pageable p) {
        return toDoRepository.findAll(p);
    }

    @Override
    public ToDo getById(Long id) {
        return toDoRepository.findById(id).orElse(null);
    }

    @Override
    public ToDo save(ToDoDTO e) {
        return toDoRepository.save(toDoMapper.map(e));
    }

    @Override
    public ToDo update(Long id, ToDo e) {
        var todo = this.getById(id);
        utils.copy(e, todo);
        return toDoRepository.save(todo);
    }

    @Override
    public ToDo delete(Long id) {
        Optional<ToDo> optionalEntity = toDoRepository.findById(id);
        if (optionalEntity.isPresent()) {
            ToDo entity = optionalEntity.get();
            toDoRepository.delete(entity);
            return entity;
        }
        return null;
    }



    @Override
    public ToDo saveToDo(Long itineraryId, GenericModel model) {

        SuggestItinerary itinerary = itRepo.findById(itineraryId).orElse(null);

        SuggestItineraryDTO dto = itineraryToDTO.map(itinerary);

        ToDoDTO toDoDto = ToDoDTO.builder()
                .withTitle(model.title())
                .withDescription(model.description())
                .withSuggestItinerary(dto)
                .build();

        ToDo newToDo = toDoRepository.save(toDoMapper.map(toDoDto));
        itinerary.getToDo().add(newToDo);
        return newToDo;
    }
}
