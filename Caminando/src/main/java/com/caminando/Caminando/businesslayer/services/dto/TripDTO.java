package com.caminando.Caminando.businesslayer.services.dto;

import com.caminando.Caminando.datalayer.entities.Image;
import com.caminando.Caminando.datalayer.entities.enums.Privacy;
import com.caminando.Caminando.datalayer.entities.enums.Status;
import com.caminando.Caminando.datalayer.entities.travel.Step;
import com.caminando.Caminando.datalayer.entities.travel.User;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class TripDTO extends BaseDTO{
    private String title;
    private String description;
    private Integer likes;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
    private Privacy privacy;
    private User user;
    private List<Step> steps;
    private Image coverImage;
}
