package com.caminando.Caminando.datalayer.repositories;

import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuggestItineraryRepository extends JpaRepository<SuggestItinerary, Long>,
        PagingAndSortingRepository<SuggestItinerary, Long> {

    Optional<SuggestItinerary> findAllByLocation(String location);

}
