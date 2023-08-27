package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    void deleteUser(Long id);

    User getUserById(Long id);

    User findUserByName(String userName);

    User saveUser(User user);

    void update(User user);

    User findUserByEmail(String userEmail);

}
