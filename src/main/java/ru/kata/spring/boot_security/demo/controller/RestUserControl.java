package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api")
public class RestUserControl {
    private UserServiceImpl userService;

    public RestUserControl(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public User getUser(Principal principal) {
        return  userService.getByUsername(principal.getName());
    }

}
