package com.example.kars.controller;

import com.example.kars.dto.user.UserControllerDto;
import com.example.kars.dto.user.UserServiceDto;
import com.example.kars.service.UserService;
import com.example.kars.service.exception.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public RedirectView register(@ModelAttribute UserControllerDto userDto) {
        var userServiceDto = new UserServiceDto();

        userServiceDto.setUsername(userDto.getUsername());
        userServiceDto.setPassword(userDto.getPassword());
        userServiceDto.setRoles(Set.of("USER"));

        userService.register(userServiceDto);

        return new RedirectView("/login");
    }


    @ExceptionHandler({UsernameAlreadyExistsException.class})
    public ModelAndView registrationError() {
        var mav = new ModelAndView("register");

        mav.setStatus(HttpStatus.CONFLICT);
        mav.addObject("error", "Username is already taken!");

        return mav;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
