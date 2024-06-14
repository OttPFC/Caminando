package com.caminando.Caminando.datalayer.entities.travel;

import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.RoleEntity;
import com.caminando.Caminando.datalayer.entities.itinerary.SuggestItinerary;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder(setterPrefix = "with")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private String username;
    @Email
    private String email;
    private String password;
    private String description;
    private String city;
    private int follow;
    private int followers;

    @OneToMany(mappedBy = "user")
    private List<Trip> trips;
    @OneToMany(mappedBy = "user")
    private List<SuggestItinerary> suggestItineraries;
    @OneToOne
    @JoinColumn(name = "profile_image_id")
    private Image profileImage;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private final List<RoleEntity> roles = new ArrayList<>();

}
