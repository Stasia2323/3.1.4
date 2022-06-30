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

    private final UserServiceImpl userService;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserServiceImpl userService, UserRepository userRepository, RoleRepository roleRepository,
                          RoleService roleService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }


    public Set<Role> getRoles(String[] roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add((Role) roleRepository.findByName(role));
        }
        return roleSet;
    }

    //показывает страничку юзера
    @GetMapping("/user")
    public String userInfo(Model model, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(auth.getName());
        model.addAttribute("user", user);
        return "user1";
    }


    // показывает страничку админа, т.е всех юзеров (удалить, апдейт, добавить)
    @GetMapping("/admin")
    public String userList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("userMain", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin1";
    }


    // должен добавлять
    @PostMapping("/admin/create")
        public String addUser(User user, @RequestParam ("listRoles") long[] roles) {
        userService.saveUser(user,roles);
        return "redirect:/admin";
    }


    @PostMapping("/admin/update")
        public String update(@ModelAttribute("user") User user, @RequestParam("listRoles") long[] roleId) {
            userService.updateUser(user, roleId);
        return "redirect:/admin";
    }

//рабочий
    @PostMapping("/admin/delete/{id}")
    public String removeUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
