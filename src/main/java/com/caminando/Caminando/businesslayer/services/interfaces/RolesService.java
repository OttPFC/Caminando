package com.caminando.Caminando.businesslayer.services.interfaces;

import com.caminando.Caminando.businesslayer.services.dto.user.RoleEntityDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.CRUDService;
import com.caminando.Caminando.datalayer.entities.RoleEntity;

public interface RolesService extends CRUDService<RoleEntity, RoleEntityDTO> {
}
