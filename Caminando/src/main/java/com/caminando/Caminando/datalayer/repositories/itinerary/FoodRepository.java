package com.caminando.Caminando.datalayer.repositories.itinerary;

import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.Food;
import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> ,
        PagingAndSortingRepository<Food, Long> {
}
