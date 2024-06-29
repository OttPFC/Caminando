package com.caminando.Caminando.businesslayer.services.impl.travel;

import com.caminando.Caminando.businesslayer.services.dto.user.RoleEntityDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RolesResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.RolesService;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.datalayer.entities.RoleEntity;
import com.caminando.Caminando.datalayer.repositories.RoleEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RoleEntityRepository roleEntityRepository;

    @Autowired
    private Mapper<RoleEntityDTO, RoleEntity> roleDTOToEntityMapper;

    @Autowired
    private Mapper<RoleEntity, RolesResponseDTO> roleEntityToResponseMapper;

    @Override
    public Page<RolesResponseDTO> getAll(Pageable pageable) {
        Page<RoleEntity> roles = roleEntityRepository.findAll(pageable);
        return roles.map(roleEntityToResponseMapper::map);
    }

    @Override
    public RolesResponseDTO getById(Long id) {
        Optional<RoleEntity> role = roleEntityRepository.findById(id);
        return role.map(roleEntityToResponseMapper::map).orElse(null);
    }

    @Override
    @Transactional
    public RolesResponseDTO save(RoleEntityDTO roleEntityDTO) {
        RoleEntity roleEntity = roleDTOToEntityMapper.map(roleEntityDTO);
        RoleEntity savedRoleEntity = roleEntityRepository.save(roleEntity);
        return roleEntityToResponseMapper.map(savedRoleEntity);
    }

    @Override
    @Transactional
    public RolesResponseDTO update(Long id, RoleEntityDTO roleEntityDTO) {
        Optional<RoleEntity> optionalRoleEntity = roleEntityRepository.findById(id);
        if (optionalRoleEntity.isPresent()) {
            RoleEntity existingRoleEntity = optionalRoleEntity.get();
            existingRoleEntity.setRoleType(roleEntityDTO.getRoleType()); // assuming RoleEntity has a name property
            RoleEntity updatedRoleEntity = roleEntityRepository.save(existingRoleEntity);
            return roleEntityToResponseMapper.map(updatedRoleEntity);
        }
        return null;
    }

    @Override
    @Transactional
    public RolesResponseDTO delete(Long id) {
        Optional<RoleEntity> optionalRoleEntity = roleEntityRepository.findById(id);
        if (optionalRoleEntity.isPresent()) {
            RoleEntity roleEntity = optionalRoleEntity.get();
            roleEntityRepository.delete(roleEntity);
            return roleEntityToResponseMapper.map(roleEntity);
        }
        return null;
    }
}
