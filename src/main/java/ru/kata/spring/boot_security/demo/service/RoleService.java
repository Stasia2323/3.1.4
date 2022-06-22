package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Set<Role> getAllRoles();
    Role findById(Long  id);
    Set<Role> findByIdRoles(String roleName);
    void addRole(Role role);
}
