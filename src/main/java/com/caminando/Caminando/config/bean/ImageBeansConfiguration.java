package com.caminando.Caminando.config.bean;

import com.caminando.Caminando.businesslayer.services.dto.ImageDTO;
import com.caminando.Caminando.businesslayer.services.dto.ImageResponseDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.datalayer.entities.Image;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ImageBeansConfiguration {

    @Bean
    @Scope("singleton")
    public Mapper<ImageResponseDTO, Image> mapImageResponseToEntity() {
        return input -> {
            Image image = new Image();
            image.setImageURL(input.getImageURL());
            image.setDescription(input.getDescription());
            return image;
        };
    }

    @Bean
    @Scope("singleton")
    public Mapper<ImageDTO, Image> mapImageDTOToEntity() {
        return input -> {
            Image image = new Image();
            image.setImageURL(input.getImageURL());
            image.setDescription(input.getDescription());
            return image;
        };
    }

    @Bean
    @Scope("singleton")
    public Mapper<Image, ImageResponseDTO> mapImageEntityToResponse() {
        return input -> ImageResponseDTO.builder()
                .withImageURL(input.getImageURL())
                .withDescription(input.getDescription())
                .build();
    }

    private ImageResponseDTO toImageDTO(Image image) {
        if (image == null) {
            return null;
        }
        return ImageResponseDTO.builder()
                .withImageURL(image.getImageURL())
                .withDescription(image.getDescription())
                .build();
    }

    private Image toImageEntity(ImageDTO imageDTO) {
        if (imageDTO == null) {
            return null;
        }
        Image image = new Image();
        image.setImageURL(imageDTO.getImageURL());
        image.setDescription(imageDTO.getDescription());
        return image;
    }
}
