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
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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

        Set<Role> roles = new HashSet<>();
        roles.add(role_admin);
        roles.add(role_user);

        roleRepository.saveAll(roles);

        user.setRoles(Collections.singleton(role_user));
        admin.setRoles(roles);

        userRepository.save(user);
        userRepository.save(admin);
    }
}
