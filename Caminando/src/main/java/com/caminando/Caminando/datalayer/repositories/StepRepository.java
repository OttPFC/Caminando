package com.caminando.Caminando.datalayer.repositories;

import com.caminando.Caminando.datalayer.entities.travel.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends JpaRepository<Step,Long> {

}
