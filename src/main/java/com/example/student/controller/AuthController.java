
package com.example.student.controller;

import com.example.student.dto.registerRequestDto;
import com.example.student.dto.registerResponseDto;
import com.example.student.dto.ResetPasswordRequest;
import com.example.student.service.AuthService;
import com.example.student.service.PasswordResetService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;

    // Registration
    @PostMapping("/register")
    public registerResponseDto registerUser(
            @RequestBody registerRequestDto request) {

        return authService.register(request);
    }

    // Send OTP
    @PostMapping("/forgot-password")
    public Map<String, String> forgotPassword(
            @RequestBody Map<String, String> request) {

        String message = passwordResetService.generateOtp(
                request.get("email")
        );

        return Map.of("message", message);
    }

    // Verify OTP
    @PostMapping("/verify-otp")
    public Map<String, String> verifyOtp(
            @RequestBody Map<String, String> request) {

        String message = passwordResetService.verifyOtp(
                request.get("email"),
                request.get("otp")
        );

        return Map.of("message", message);
    }

    // Reset password
    @PostMapping("/reset-password")
    public Map<String, String> resetPassword(
            @RequestBody ResetPasswordRequest request) {

        System.out.println("FORGOT PASSWORD CONTROLLER REACHED");

        String message = passwordResetService.resetPassword(
                request.getEmail(),
                request.getNewPassword()
        );
        return Map.of("message", message);
    }
}
