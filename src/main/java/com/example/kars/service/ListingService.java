package com.example.kars.service;

import com.example.kars.dto.post.ListingServiceDto;
import com.example.kars.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    public List<ListingServiceDto> getAll() {
        return listingRepository.getAllOrderedByTime();
    }

    public void create(ListingServiceDto listing) {
        listingRepository.create(listing);
    }
}
