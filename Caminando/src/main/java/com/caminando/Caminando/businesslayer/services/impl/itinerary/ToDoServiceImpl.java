package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.ToDoDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.ToDoService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.ToDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ToDoServiceImpl implements ToDoService {
    @Override
    public Page<ToDo> getAll(Pageable p) {
        return null;
    }

    @Override
    public ToDo getById(Long id) {
        return null;
    }

    @Override
    public ToDo save(ToDoDTO e) {
        return null;
    }

    @Override
    public ToDo update(Long id, ToDo e) {
        return null;
    }

    @Override
    public ToDo delete(Long id) {
        return null;
    }
}
