package com.caminando.Caminando.datalayer.entities.travel;

import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.enums.Privacy;
import com.caminando.Caminando.datalayer.entities.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "trip")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder(setterPrefix = "with")
public class Trip extends BaseEntity{

    private String title;
    private String description;
    private Integer likes;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "trip")
    private List<Step> steps;

    @OneToOne
    @JoinColumn(name = "cover_image_id")
    private Image coverImage;
}
