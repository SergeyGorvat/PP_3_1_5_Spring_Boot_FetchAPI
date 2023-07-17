package ru.kata.spring.boot_security.demo.loaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collections;

@Component
public class DataLoader implements ApplicationRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public DataLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    @Override
    public void run(ApplicationArguments args) {
        User user = new User("user", 20,
                "user@mail.ru", new BCryptPasswordEncoder().encode("user"));
        User admin = new User("admin", 20,
                "admin@mail.ru", new BCryptPasswordEncoder().encode("admin"));

        Role role_admin = new Role("ROLE_ADMIN");
        Role role_user = new Role("ROLE_USER");

        roleRepository.save(role_admin);
        roleRepository.save(role_user);

        user.setRoles(Collections.singleton(role_user));
        admin.setRoles(Collections.singleton(role_admin));

        userRepository.save(user);
        userRepository.save(admin);
    }
}
