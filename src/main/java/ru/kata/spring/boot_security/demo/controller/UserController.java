package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {


    private final RoleService roleService;

    public UserController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("/user")
    public String userInfo() {
        return "user1";
    }

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("amountOfRoles", roleService.getAllRoles());
        return "admin1";
    }


}
