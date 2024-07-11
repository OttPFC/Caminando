package com.caminando.Caminando.datalayer.entities.travel;

import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.enums.Privacy;
import com.caminando.Caminando.datalayer.entities.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Builder(builderClassName = "TripBuilder", setterPrefix = "with")
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
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference(value = "trip-steps")
    private List<Step> steps = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_image_id")
    @JsonManagedReference
    private Image coverImage;

    public void addStep(Step step) {
        this.steps.add(step);
        step.setTrip(this);
    }

    public void setCoverImage(Image coverImage) {
        this.coverImage = coverImage;
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

    public static class TripBuilder {
        private Long id;
        private String title;
        private String description;
        private Integer likes;
        private LocalDate startDate;
        private LocalDate endDate;
        private Status status;
        private Privacy privacy;
        private User user;
        private List<Step> steps = new ArrayList<>();
        private Image coverImage;

        TripBuilder() {
        }

        public TripBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public TripBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public TripBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public TripBuilder withLikes(Integer likes) {
            this.likes = likes;
            return this;
        }

        public TripBuilder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public TripBuilder withEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public TripBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public TripBuilder withPrivacy(Privacy privacy) {
            this.privacy = privacy;
            return this;
        }

        public TripBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public TripBuilder withSteps(List<Step> steps) {
            this.steps = steps;
            return this;
        }

        public TripBuilder withCoverImage(Image coverImage) {
            this.coverImage = coverImage;
            return this;
        }

        public Trip build() {
            Trip trip = new Trip();
            trip.setId(this.id);
            trip.setTitle(this.title);
            trip.setDescription(this.description);
            trip.setLikes(this.likes);
            trip.setStartDate(this.startDate);
            trip.setEndDate(this.endDate);
            trip.setStatus(this.status);
            trip.setPrivacy(this.privacy);
            trip.setUser(this.user);
            trip.setSteps(this.steps);
            trip.setCoverImage(this.coverImage);
            return trip;
        }

        public String toString() {
            return "Trip.TripBuilder(id=" + this.id + ", title=" + this.title + ", description=" + this.description + ", likes=" + this.likes + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ", status=" + this.status + ", privacy=" + this.privacy + ", user=" + this.user + ", steps=" + this.steps + ", coverImage=" + this.coverImage + ")";
        }
    }
}
