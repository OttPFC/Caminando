package com.caminando.Caminando.businesslayer.services.interfaces.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.SuggestItineraryDTO;
import com.caminando.Caminando.businesslayer.services.impl.itinerary.SuggestItineraryServiceImpl;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.presentationlayer.api.models.itinerary.SuggestItineraryModel;

public interface SuggestItineraryService extends CRUDService<SuggestItinerary, SuggestItineraryDTO> {

    SuggestItinerary saveItinerary(SuggestItineraryModel model);
}
