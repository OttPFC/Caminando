package com.caminando.Caminando.datalayer.repositories;

import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.PlaceToStay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceToStayRepository extends JpaRepository<PlaceToStay, Long> {
}
