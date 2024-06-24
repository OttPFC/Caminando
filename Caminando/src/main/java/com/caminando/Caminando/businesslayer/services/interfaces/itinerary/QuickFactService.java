package com.caminando.Caminando.businesslayer.services.interfaces.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.QuickFactsDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.QuickFacts;
import com.caminando.Caminando.presentationlayer.api.models.itinerary.GenericModel;

import java.security.CodeSigner;

public interface QuickFactService extends CRUDService<QuickFacts, QuickFactsDTO> {

    QuickFacts saveQuickFact(Long itineraryId, GenericModel model);
}
