package com.example.kars.service;

import com.example.kars.dto.user.UserServiceDto;
import com.example.kars.service.exception.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

public class UserService {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder encoder;

    public void register(UserServiceDto user) throws UsernameAlreadyExistsException {
        if (userDetailsManager.userExists(user.getUsername())) {
            throw new UsernameAlreadyExistsException(
                String.format("Could not create user with username '%s' because it already exists", user.getUsername())
            );
        }

        userDetailsManager.createUser(
            User.builder()
                .passwordEncoder(encoder::encode)
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(String[]::new))
                .build()
        );
    }
}
