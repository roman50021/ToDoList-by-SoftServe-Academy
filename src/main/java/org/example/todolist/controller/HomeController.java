package org.example.todolist.controller;

import org.example.todolist.service.UserService;
import org.example.todolist.service.impl.UserDetailsServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final UserService userService;
    public HomeController(UserService userService, UserDetailsServiceImp userDetailsServiceImp) {
        this.userService = userService;
    }

    @GetMapping({"/home"})
    public String home(Model model) {
        model.addAttribute("users", userService.getAll());
        return "home";
    }
}
