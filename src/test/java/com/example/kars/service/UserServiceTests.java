package com.example.kars.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.kars.dto.user.UserServiceDto;
import com.example.kars.service.exception.UsernameAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    UserDetailsManager userDetailsManager;

    @InjectMocks
    UserService userService;

    @Test
    void whenUserRegistersWithTakenUsername_thenThrowsException() {
        when(userDetailsManager.userExists(any())).thenReturn(true);

        var dto = new UserServiceDto();

        dto.setUsername("username");
        dto.setPassword("password");
        dto.setRoles(Set.of("USER"));

        assertThrows(
            UsernameAlreadyExistsException.class,
            () -> userService.register(dto),
            "When username is taken then UsernameAlreadyExistsException should be thrown"
        );
    }

}
