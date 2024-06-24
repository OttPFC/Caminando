package com.caminando.Caminando.businesslayer.services.interfaces.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.RestaurantDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.Restaurant;

public interface RestaurantService extends CRUDService<Restaurant, RestaurantDTO> {
}
