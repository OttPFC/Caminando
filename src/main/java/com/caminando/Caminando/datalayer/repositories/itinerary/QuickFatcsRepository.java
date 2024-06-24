package com.caminando.Caminando.datalayer.repositories.itinerary;

import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.QuickFacts;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuickFatcsRepository extends JpaRepository<QuickFacts, Long>,
        PagingAndSortingRepository<QuickFacts, Long> {
}
