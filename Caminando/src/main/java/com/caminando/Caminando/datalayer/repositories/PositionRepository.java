package com.caminando.Caminando.datalayer.repositories;

import com.caminando.Caminando.datalayer.entities.travel.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position,Long> {
}
