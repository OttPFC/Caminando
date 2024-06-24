package com.caminando.Caminando.businesslayer.services.interfaces.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.RestaurantDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.Restaurant;
import com.caminando.Caminando.presentationlayer.api.models.itinerary.GenericModel;

public interface RestaurantService extends CRUDService<Restaurant, RestaurantDTO> {


    Restaurant saveRestaurant(Long itineraryId, GenericModel model);

}
