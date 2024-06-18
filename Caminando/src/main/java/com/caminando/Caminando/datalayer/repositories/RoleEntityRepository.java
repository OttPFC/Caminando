package com.caminando.Caminando.datalayer.repositories;

import com.caminando.Caminando.datalayer.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long>,
        PagingAndSortingRepository<RoleEntity, Long> {
    Optional<RoleEntity> findOneByRoleType(String roleType);
}
