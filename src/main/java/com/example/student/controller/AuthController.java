package com.example.student.controller;

import com.example.student.dto.registerRequestDto;
import com.example.student.dto.registerResponseDto;
import com.example.student.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public registerResponseDto registerUser(@RequestBody registerRequestDto  registerRequestDto) {
        return authService.register(registerRequestDto);
    }
}
