package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();
    User getUserById(Integer id);
    boolean saveUser(User user);
    void updateUser(Integer id, User updatedUser);
    boolean deleteUser(Integer id);
}
