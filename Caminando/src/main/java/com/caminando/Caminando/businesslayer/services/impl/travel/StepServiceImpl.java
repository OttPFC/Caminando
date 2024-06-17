package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.travel.StepService;
import com.caminando.Caminando.datalayer.entities.travel.BaseEntity;
import com.caminando.Caminando.datalayer.repositories.StepRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StepServiceImpl implements StepService {

    @Autowired
    private StepRepository step;
    @Override
    public Page getAll(Pageable p) {
        return step.findAll(p);
    }

    @Override
    public BaseEntity getById(Long id) {
        return null;
    }

    @Override
    public BaseEntity save(BaseDTO e) {
        return null;
    }

    @Override
    public BaseEntity update(Long id, BaseEntity e) {
        return null;
    }

    @Override
    public BaseEntity delete(Long id) {
        return null;
    }
}
