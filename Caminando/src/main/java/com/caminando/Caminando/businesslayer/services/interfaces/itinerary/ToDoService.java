package com.caminando.Caminando.businesslayer.services.interfaces.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.ToDoDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.ToDo;
import com.caminando.Caminando.presentationlayer.api.models.itinerary.GenericModel;

public interface ToDoService extends CRUDService<ToDo, ToDoDTO> {

    ToDo saveToDo(Long itineraryId, GenericModel model);
}
