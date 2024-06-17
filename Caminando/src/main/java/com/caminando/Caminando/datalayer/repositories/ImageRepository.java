package com.caminando.Caminando.datalayer.repositories;

import com.caminando.Caminando.datalayer.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
