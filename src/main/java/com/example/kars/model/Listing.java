package com.example.kars.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Listing {

    private String username;
    private String title;
    private String description;
    private String base64ImageJpg;
    private Integer price;

}
