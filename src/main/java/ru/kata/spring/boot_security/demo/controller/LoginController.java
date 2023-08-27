package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.ManyToMany;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String getLogin() {
        return "/user/login";
    }
}
