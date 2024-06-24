package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.ToDoDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.ToDoService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.ToDo;
import com.caminando.Caminando.datalayer.repositories.itinerary.ToDoRepository;
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
}
