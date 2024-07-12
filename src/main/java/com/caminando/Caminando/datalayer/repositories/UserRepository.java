package com.caminando.Caminando.datalayer.repositories;

import com.caminando.Caminando.datalayer.entities.RoleEntity;
import com.caminando.Caminando.datalayer.entities.travel.BaseEntity;
import com.caminando.Caminando.datalayer.entities.travel.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, PagingAndSortingRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findOneByUsername(String username);
    List<User> findByRoles_RoleType(String role);
    List<User> findByFirstNameContainingIgnoreCase(String firstName);
}
