package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class RestAdminControl {
    private UserServiceImpl userService;

    public RestAdminControl(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/admin")
    public ResponseEntity<List<User>> userList() {
        final List<User> clients = userService.getAllUsers();

        return clients != null &&  !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/admin/new")
    public List<User> addUser(@RequestBody User user) {
        userService.saveUser(user);
        return userService.getAllUsers();
    }

    @PutMapping(value = "/admin/edit")
    public ResponseEntity<?> update(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



    @DeleteMapping(value = "/admin/{id}")
    public ResponseEntity<?> removeUser(@PathVariable(name = "id") Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
