package com.example.kars.controller;

import com.example.kars.dto.post.ListingControllerDto;
import com.example.kars.dto.post.ListingServiceDto;
import com.example.kars.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.security.Principal;

@Controller
public class ListingController {

    @Autowired
    ListingService listingService;

    @GetMapping("/post")
    public String post() {
        return "post";
    }

    @PostMapping("/post")
    public RedirectView post(@ModelAttribute ListingControllerDto dto, Principal principal) throws IOException {
        var serviceDto = new ListingServiceDto();

        serviceDto.setUsername(principal.getName());
        serviceDto.setTitle(dto.getTitle());
        serviceDto.setDescription(dto.getDescription());
        serviceDto.setPrice(dto.getPrice());
        serviceDto.setPicture(
            ImageIO.read(
                dto.getPicture().getInputStream()
            )
        );

        listingService.create(serviceDto);

        return new RedirectView("/");
    }
}
