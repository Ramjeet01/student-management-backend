package com.example.student.controller;

import com.example.student.dto.loginRequestDto;
import com.example.student.dto.loginResponseDto;
import com.example.student.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public loginResponseDto login(@RequestBody loginRequestDto loginRequestDto) {
        Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

        String jwtToken = jwtService.generateToken(authenticationResponse);

        return new loginResponseDto(jwtToken);

    }

    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(Authentication authentication) {

        return Map.of(
                "username", authentication.getName(),
                "roles", authentication.getAuthorities()
                        .stream()
                        .map(authority -> authority.getAuthority())
                        .collect(Collectors.toList())
        );
    }
}
