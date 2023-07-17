package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user")
    public String userPage (Model model, Principal principal) {
        User user = userRepository.findByUserName(principal.getName()).orElse(null);
        model.addAttribute("userDetails", user);
        return "view_user/user";
    }
}