package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();
     List<Role> getAllRoles();
    User getUserById(Integer id);
    boolean saveUser(User user);
    void updateUser(User updatedUser);
    boolean deleteUser(Integer id);
}
