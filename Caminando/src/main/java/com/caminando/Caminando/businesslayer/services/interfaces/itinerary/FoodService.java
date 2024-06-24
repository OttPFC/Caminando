package com.caminando.Caminando.businesslayer.services.interfaces.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.FoodDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.Food;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.PlaceToStay;
import com.caminando.Caminando.presentationlayer.api.models.itinerary.GenericModel;

public interface FoodService extends CRUDService<Food, FoodDTO> {

    Food saveFood(Long itineraryId, GenericModel model);
}
