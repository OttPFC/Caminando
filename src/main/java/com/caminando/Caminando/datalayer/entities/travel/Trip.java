package com.caminando.Caminando.datalayer.entities.travel;

import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.enums.Privacy;
import com.caminando.Caminando.datalayer.entities.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trip")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder(setterPrefix = "with")
public class Trip extends BaseEntity {

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 50, nullable = false)
    private String description;

    private Integer likes;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Privacy privacy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Step> steps = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_image_id")
    @JsonManagedReference
    private Image coverImage;

    public void addStep(Step step) {
        this.steps.add(step);
        step.setTrip(this);
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", likes=" + likes +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", privacy=" + privacy +
                ", stepsCount=" + (steps != null ? steps.size() : 0) +
                ", coverImage=" + (coverImage != null ? coverImage.getImageURL() : "null") +
                '}';
    }
}

