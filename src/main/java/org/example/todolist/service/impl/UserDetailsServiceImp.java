package org.example.todolist.service.impl;

import org.example.todolist.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public boolean authenticateByEmail(String email, String password){
        UserDetails userDetails = loadUserByUsername(email);
        return passwordEncoder.matches(password, userDetails.getPassword());
    }
}
