package com.caminando.Caminando.businesslayer.services.impl.itinerary;

import com.caminando.Caminando.businesslayer.services.dto.itinerary.QuickFatcsDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.itinerary.QuickFactService;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.QuickFacts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuickFactServiceImpl implements QuickFactService {
    @Override
    public Page<QuickFacts> getAll(Pageable p) {
        return null;
    }

    @Override
    public QuickFacts getById(Long id) {
        return null;
    }

    @Override
    public QuickFacts save(QuickFatcsDTO e) {
        return null;
    }

    @Override
    public QuickFacts update(Long id, QuickFacts e) {
        return null;
    }

    @Override
    public QuickFacts delete(Long id) {
        return null;
    }
}
