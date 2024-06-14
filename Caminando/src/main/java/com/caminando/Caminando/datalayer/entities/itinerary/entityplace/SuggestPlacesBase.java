package com.caminando.Caminando.datalayer.entities.itinerary.entityplace;

import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class SuggestPlacesBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "place_base_entity_seq")
    @SequenceGenerator(name = "place_base_entity_seq", sequenceName = "place_base_entity_sequence", allocationSize = 1)
    private Long id;

}
