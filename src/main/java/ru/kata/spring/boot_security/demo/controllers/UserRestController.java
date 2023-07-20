package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.User;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @GetMapping
    public ModelAndView getWebPageForUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user_page");
        return modelAndView;
    }

    @GetMapping("/auth_data")
    public ResponseEntity<User> getAuthenticationData(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }


}
