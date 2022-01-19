package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    void deleteUser(Long id);

    User getUserById(Long id);

    User findUserByName(String userName);

    void update(Long id, User user);

    User saveUser(User user);

    Set<Role> getSetOfRoles(List<String> rolesId);
}
