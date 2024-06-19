package com.caminando.Caminando.businesslayer.services.interfaces.generic;

import com.caminando.Caminando.businesslayer.services.dto.BaseDTO;
import com.caminando.Caminando.datalayer.entities.travel.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CRUDService <T extends BaseEntity, A extends BaseDTO> {
    Page<T> getAll(Pageable p);

    T getById(Long id);

    T save(A e);

    T update(Long id, T e);

    T delete(Long id);
}
