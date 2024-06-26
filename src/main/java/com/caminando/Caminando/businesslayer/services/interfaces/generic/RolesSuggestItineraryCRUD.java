package com.caminando.Caminando.businesslayer.services.interfaces.generic;

import com.caminando.Caminando.datalayer.entities.travel.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RolesSuggestItineraryCRUD <T extends BaseEntity, A> {
    Page<T> getAll(Pageable p);

    T save(A e);

    T delete(Long id);
}
