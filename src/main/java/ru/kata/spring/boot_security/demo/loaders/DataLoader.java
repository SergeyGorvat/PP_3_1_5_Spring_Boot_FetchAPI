package ru.kata.spring.boot_security.demo.loaders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) {

        User user = new User("user", new BCryptPasswordEncoder().encode("user"));
        User admin = new User("admin", new BCryptPasswordEncoder().encode("admin"));

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
