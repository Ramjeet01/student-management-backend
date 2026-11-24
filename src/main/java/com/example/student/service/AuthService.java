package com.example.student.service;

import com.example.student.dto.registerRequestDto;
import com.example.student.dto.registerResponseDto;
import com.example.student.entity.User;
import com.example.student.repository.UserRepository;
import com.example.student.repository.studentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public registerResponseDto register(registerRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        user.setRole("ROLE_USER");

        User savedUser = userRepository.save(user);

        registerResponseDto response = new registerResponseDto();

        response.setUsername(savedUser.getUsername());

        return response;
    }

}
