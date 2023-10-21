package com.example.kars.config;

import com.example.kars.repository.ListingRepository;
import com.example.kars.service.ListingService;
import com.example.kars.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public ListingService postService() {
        return new ListingService();
    }

    @Bean
    public ListingRepository postRepository() {
        return new ListingRepository();
    }

}
