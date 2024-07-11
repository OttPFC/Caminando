package com.caminando.Caminando.datalayer.entities.travel;

import com.caminando.Caminando.datalayer.entities.Image;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "step")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder(setterPrefix = "with")
public class Step extends BaseEntity {

    @Column(length = 250, nullable = false)
    private String description;

    private Integer likes;

    @Column(nullable = false)
    private LocalDate arrivalDate;

    @Column(nullable = false)
    private LocalDate departureDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "trip_id")
    @JsonBackReference(value = "trip-steps")
    private Trip trip;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference(value = "step-comments")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference(value = "step-images")
    @BatchSize(size = 10)
    private List<Image> images = new ArrayList<>();

    @OneToOne(mappedBy = "step", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference(value = "step-position")
    private Position position;

    @Override
    public String toString() {
        return "Step{" +
                "id=" + getId() +
                ", description='" + description + '\'' +
                ", likes=" + likes +
                ", arrivalDate=" + arrivalDate +
                ", departureDate=" + departureDate +
                '}';
    }


}
