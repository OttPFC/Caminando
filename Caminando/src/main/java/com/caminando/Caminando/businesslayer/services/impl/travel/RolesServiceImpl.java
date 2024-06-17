package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.user.RoleEntityDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.RolesService;
import com.caminando.Caminando.datalayer.entities.RoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RolesServiceImpl implements RolesService {
    @Override
    public Page<RoleEntity> getAll(Pageable p) {
        return null;
    }

    @Override
    public RoleEntity getById(Long id) {
        return null;
    }

    @Override
    public RoleEntity save(RoleEntityDTO e) {
        return null;
    }

    @Override
    public RoleEntity update(Long id, RoleEntity e) {
        return null;
    }

    @Override
    public RoleEntity delete(Long id) {
        return null;
    }
}
