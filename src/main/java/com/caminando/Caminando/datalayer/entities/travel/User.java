package com.caminando.Caminando.datalayer.entities.travel;

import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.RoleEntity;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder(setterPrefix = "with")
public class User extends BaseEntity {

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false, unique = true)
    @Email
    private String email;

    @Column(length = 125, nullable = false)
    private String password;

    @Column(length = 50)
    private String bio;

    @Column(length = 50)
    private String city;

    private Long follow = 0L;

    private Long followers = 0L;

    @Column(nullable = false)
    private boolean isEnabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    @JsonManagedReference
    @ToString.Exclude
    private List<Trip> trips = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    @JsonManagedReference
    @ToString.Exclude
    private List<SuggestItinerary> suggestItineraries = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_image_id")
    private Image profileImage;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RoleEntity> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Position> positions = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                ", city='" + city + '\'' +
                ", follow=" + follow +
                ", followers=" + followers +
                ", isEnabled=" + isEnabled +
                ", tripsCount=" + (trips != null ? trips.size() : 0) +
                ", suggestItinerariesCount=" + (suggestItineraries != null ? suggestItineraries.size() : 0) +
                ", roles=" + roles +
                ", positionsCount=" + (positions != null ? positions.size() : 0) +
                '}';
    }
}

