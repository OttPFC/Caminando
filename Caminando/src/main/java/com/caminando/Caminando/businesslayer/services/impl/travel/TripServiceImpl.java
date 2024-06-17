package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.travel.TripDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.TripService;
import com.caminando.Caminando.datalayer.entities.travel.Trip;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TripServiceImpl implements TripService {
    @Override
    public Page<Trip> getAll(Pageable p) {
        return null;
    }

    @Override
    public Trip getById(Long id) {
        return null;
    }

    @Override
    public Trip save(TripDTO e) {
        return null;
    }

    @Override
    public Trip update(Long id, Trip e) {
        return null;
    }

    @Override
    public Trip delete(Long id) {
        return null;
    }
}
