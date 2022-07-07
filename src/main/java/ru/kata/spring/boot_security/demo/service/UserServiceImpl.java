package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private PasswordEncoder passwordEncoder;
    private Set<Role> roles;

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                         @Lazy   PasswordEncoder PasswordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = PasswordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }

    @Override
    public User getById(Integer id) {
        return userRepository.getById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }


    @Transactional
    @Override
    public void saveUser(User user, long[] listRoles) {
        Set<Role> rolesSet = new HashSet<>();
        for (int i = 0; i < listRoles.length; i++) {
            rolesSet.add(roleRepository.findById(listRoles[i]));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(rolesSet);
        userRepository.save(user);
    }


    @Transactional
    @Override
    public void updateUser(User user) {
        if (user.getPassword().startsWith("$2a$10$") && user.getPassword().length() == 60) {
            user.setPassword(user.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username);
    }
}


