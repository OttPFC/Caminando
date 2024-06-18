package com.caminando.Caminando.datalayer.repositories;

import com.caminando.Caminando.datalayer.entities.itinerary.entityplace.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo,Long>,
        PagingAndSortingRepository<ToDo, Long> {
}
