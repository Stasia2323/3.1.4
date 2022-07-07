package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getById(Integer id);
    void saveUser(User user);
    void deleteUser(Integer id);
    public void saveUser(User user, long[] listRoles);
    public void updateUser(User user);

    public User getByUsername(String username);
}
