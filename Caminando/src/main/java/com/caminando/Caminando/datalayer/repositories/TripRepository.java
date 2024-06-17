package com.caminando.Caminando.datalayer.repositories;

import com.caminando.Caminando.datalayer.entities.travel.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    Optional<Trip> findOneByTitle(String title);
}
