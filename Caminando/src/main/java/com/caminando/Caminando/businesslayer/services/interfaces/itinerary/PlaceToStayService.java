package com.caminando.Caminando.businesslayer.services.interfaces.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.PlaceToStayDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.PlaceToStay;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.QuickFacts;
import com.caminando.Caminando.presentationlayer.api.models.itinerary.GenericModel;

public interface PlaceToStayService extends CRUDService<PlaceToStay, PlaceToStayDTO> {

    PlaceToStay savePlaceToStay(Long itineraryId, GenericModel model);
}
