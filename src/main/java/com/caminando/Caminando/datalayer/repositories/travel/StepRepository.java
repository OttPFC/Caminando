package com.caminando.Caminando.datalayer.repositories.travel;

import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.ToDo;
import com.caminando.Caminando.datalayer.entities.travel.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends JpaRepository<Step,Long>,
        PagingAndSortingRepository<Step, Long> {

}
