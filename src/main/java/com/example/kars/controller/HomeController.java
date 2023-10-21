package com.example.kars.controller;

import com.example.kars.dto.post.ListingServiceDto;
import com.example.kars.model.Listing;
import com.example.kars.service.ListingService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class HomeController {

    @Autowired
    private ListingService listingService;

    @GetMapping("/")
    public ModelAndView home() {
        var mav = new ModelAndView("index");
        var listings = listingService.getAll();

        var model = listings.stream()
                            .map(this::convertToModel)
                            .toList();

        mav.addObject("listings", model);

        return mav;
    }

    private Listing convertToModel(ListingServiceDto listingServiceDto) {
        var listing = new Listing();

        listing.setUsername(listingServiceDto.getUsername());
        listing.setDescription(listingServiceDto.getDescription());
        listing.setTitle(listingServiceDto.getTitle());
        listing.setPrice(listingServiceDto.getPrice());
        listing.setBase64ImageJpg(
            convertImageJpgToBase64(listingServiceDto.getPicture())
        );

        return listing;
    }

    private String convertImageJpgToBase64(BufferedImage image) {
        var baos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "jpg", baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Base64().encodeAsString(baos.toByteArray());
    }
}
