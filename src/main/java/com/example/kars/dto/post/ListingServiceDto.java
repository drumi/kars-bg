package com.example.kars.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.awt.image.BufferedImage;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ListingServiceDto {

    private String username;
    private String title;
    private Integer price;
    private String description;
    private BufferedImage picture;

}