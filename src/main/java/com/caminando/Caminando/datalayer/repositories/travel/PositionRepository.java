package com.caminando.Caminando.datalayer.repositories.travel;

import com.caminando.Caminando.datalayer.entities.travel.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position,Long>,
        PagingAndSortingRepository<Position, Long> {

}
