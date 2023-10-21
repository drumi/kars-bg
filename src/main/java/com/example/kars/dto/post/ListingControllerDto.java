package com.example.kars.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ListingControllerDto {

    private String title;
    private Integer price;
    private String description;
    private MultipartFile picture;

}
