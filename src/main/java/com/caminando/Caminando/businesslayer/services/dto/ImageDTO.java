package com.caminando.Caminando.businesslayer.services.dto;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class ImageDTO extends BaseDTO{
    private Long id;
    private String imageURL;
    private String description;
}
