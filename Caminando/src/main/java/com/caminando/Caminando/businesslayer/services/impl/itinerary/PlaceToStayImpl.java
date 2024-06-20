package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.PlaceToStayDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.PlaceToStayService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.PlaceToStay;
import com.caminando.Caminando.datalayer.repositories.itinerary.PlaceToStayRepository;
import com.caminando.Caminando.presentationlayer.utility.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PlaceToStayImpl implements PlaceToStayService {


    @Autowired
    PlaceToStayRepository repo;

    @Autowired
    Mapper<PlaceToStayDTO,PlaceToStay> mapper;

    @Autowired
    EntityUtils utils;


    @Override
    public Page<PlaceToStay> getAll(Pageable p) {
        return repo.findAll(p);
    }

    @Override
    public PlaceToStay getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public PlaceToStay save(PlaceToStayDTO e) {
        return repo.save(mapper.map(e));
    }

    @Override
    public PlaceToStay update(Long id, PlaceToStay e) {
        var entity = this.getById(id);
        utils.copy(e,entity);
        return repo.save(entity);
    }

    @Override
    public PlaceToStay delete(Long id) {
        Optional<PlaceToStay> optionalEntity = repo.findById(id);
        if (optionalEntity.isPresent()) {
            PlaceToStay entity = optionalEntity.get();
            repo.delete(entity);
            return entity;
        }
        return null;
    }
}
