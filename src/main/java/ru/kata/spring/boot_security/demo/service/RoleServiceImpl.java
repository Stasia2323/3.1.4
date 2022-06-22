package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Set<Role> getAllRoles() {
        return  new HashSet<>(roleRepository.findAll());
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.getById(id);
    }

    @Override
    public Set<Role> findByIdRoles(String roleName) {
        return (Set<Role>) roleRepository.findByName(roleName);
    }

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);

    }
}
