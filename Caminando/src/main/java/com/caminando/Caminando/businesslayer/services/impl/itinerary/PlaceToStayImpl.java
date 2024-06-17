package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.PlaceToStayDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.PlaceToStayService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.PlaceToStay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlaceToStayImpl implements PlaceToStayService {
    @Override
    public Page<PlaceToStay> getAll(Pageable p) {
        return null;
    }

    @Override
    public PlaceToStay getById(Long id) {
        return null;
    }

    @Override
    public PlaceToStay save(PlaceToStayDTO e) {
        return null;
    }

    @Override
    public PlaceToStay update(Long id, PlaceToStay e) {
        return null;
    }

    @Override
    public PlaceToStay delete(Long id) {
        return null;
    }
}
