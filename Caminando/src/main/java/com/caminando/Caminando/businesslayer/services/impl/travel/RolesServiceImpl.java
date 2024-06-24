package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.user.RoleEntityDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.RolesService;
import com.caminando.Caminando.datalayer.entities.RoleEntity;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.ToDo;
import com.caminando.Caminando.datalayer.repositories.RoleEntityRepository;
import com.caminando.Caminando.presentationlayer.utility.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class RolesServiceImpl implements RolesService {

    @Autowired
    RoleEntityRepository roles;

    @Autowired
    EntityUtils utils;

    @Override
    public Page<RoleEntity> getAll(Pageable p) {
        return roles.findAll(p);
    }

    @Override
    public RoleEntity getById(Long id) {
        Optional<RoleEntity> role = roles.findById(id);
        if (role.isPresent()) {
            return role.get();
        } else {
            log.error("Role with ID {} not found", id);
            return null;
        }
    }

    @Override
    public RoleEntity save(RoleEntityDTO e) {
        RoleEntity role = RoleEntity.builder()
                .withRoleType(e.getRoleType())
                .build();
        return roles.save(role);
    }

    @Override
    public RoleEntity update(Long id, RoleEntity e) {
            var role = this.getById(id);
            utils.copy(e, role);
            return roles.save(role);
        }

    @Override
    public RoleEntity delete(Long id) {
        Optional<RoleEntity> roleOptional = roles.findById(id);
        if (roleOptional.isPresent()) {
            RoleEntity role = roleOptional.get();
            roles.delete(role);
            return role;
        } else {
            log.error("Role with ID {} not found for deletion", id);
            return null;
        }
    }
}

