package com.caminando.Caminando.config.bean;

import com.caminando.Caminando.businesslayer.services.dto.user.RolesResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.datalayer.entities.RoleEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class RoleBeanConfiguration {

    @Bean
    @Scope("singleton")
    public Mapper<RoleEntity, RolesResponseDTO> mapRoleEntityToDTO() {
        return roleEntity -> {
            if (roleEntity == null) {
                return null;
            }
            return RolesResponseDTO.builder()
                    .withRoleType(roleEntity.getRoleType())
                    .build();
        };
    }

    @Bean
    @Scope("singleton")
    public Mapper<RolesResponseDTO, RoleEntity> mapRoleDTOToEntity() {
        return roleDTO -> {
            if (roleDTO == null) {
                return null;
            }
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setRoleType(roleDTO.getRoleType());
            return roleEntity;
        };
    }

    @Bean
    @Scope("singleton")
    public Mapper<List<RoleEntity>, List<RolesResponseDTO>> mapRoleEntityListToDTOList(Mapper<RoleEntity, RolesResponseDTO> roleEntityToDTOMapper) {
        return roleEntities -> {
            if (roleEntities == null) {
                return null;
            }
            return roleEntities.stream()
                    .map(roleEntityToDTOMapper::map)
                    .collect(Collectors.toList());
        };
    }

    @Bean
    @Scope("singleton")
    public Mapper<List<RolesResponseDTO>, List<RoleEntity>> mapRoleDTOListToEntityList(Mapper<RolesResponseDTO, RoleEntity> roleDTOToEntityMapper) {
        return roleDTOs -> {
            if (roleDTOs == null) {
                return null;
            }
            return roleDTOs.stream()
                    .map(roleDTOToEntityMapper::map)
                    .collect(Collectors.toList());
        };
    }
}
