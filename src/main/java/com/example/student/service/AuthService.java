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

    public registerResponseDto register(registerRequestDto registerRequestDto) {

        User user = new User();

        user.setUsername(registerRequestDto.getUsername());
        user.setPassword(
                passwordEncoder.encode(registerRequestDto.getPassword())
        );
        user.setEnabled(true);
        user.setRole("ROLE_USER");

        User resultedUser = userRepository.save(user);

        registerResponseDto registerResponseDto = new registerResponseDto();

        registerResponseDto.setUsername(resultedUser.getUsername());
        registerResponseDto.setPassword(resultedUser.getPassword());

        return registerResponseDto;
    }
}
