package org.example.todolist.controller;

import org.example.todolist.dto.LoginUserDto;
import org.example.todolist.repository.UserRepository;
import org.example.todolist.service.impl.UserDetailsServiceImp;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    public final UserDetailsServiceImp userDetailsServiceImp;
    private final UserRepository userRepository;

    public AuthController(UserDetailsServiceImp userDetailsServiceImp, UserRepository userRepository) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        LoginUserDto loginUserDto = new LoginUserDto();
        model.addAttribute("loginUserDto", loginUserDto);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginUserDto") LoginUserDto loginUserDto,
                        RedirectAttributes redirectAttributes) {

        String email = loginUserDto.getEmail();
        String password = loginUserDto.getPassword();

        UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(email);
        if (userDetails != null && userDetailsServiceImp.authenticateByEmail(email, password)) {
            return "redirect:/todos/all/users/" + userRepository.findByEmail(email).getId();
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid Email or Password");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}


