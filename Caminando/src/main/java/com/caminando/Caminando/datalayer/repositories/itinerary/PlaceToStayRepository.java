package com.caminando.Caminando.datalayer.repositories.itinerary;

import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.PlaceToStay;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceToStayRepository extends JpaRepository<PlaceToStay, Long> ,
        PagingAndSortingRepository<PlaceToStay, Long> {
}
