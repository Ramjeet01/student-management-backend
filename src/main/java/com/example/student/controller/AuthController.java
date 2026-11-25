
package com.example.student.controller;

import com.example.student.dto.registerRequestDto;
import com.example.student.dto.ResetPasswordRequest;
import com.example.student.service.RegistrationService;
import com.example.student.service.PasswordResetService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;
    private final PasswordResetService passwordResetService;

    // Start registration and send email OTP
    @PostMapping("/register")
    public Map<String, String> registerUser(
            @Valid @RequestBody registerRequestDto request) {

        String message =
                registrationService.startRegistration(request);

        return Map.of("message", message);
    }

    // Verify registration OTP and create account
    @PostMapping("/verify-registration")
    public Map<String, String> verifyRegistration(
            @RequestBody Map<String, String> request) {

        String message = registrationService.verifyRegistration(
                request.get("email"),
                request.get("otp")
        );

        return Map.of("message", message);
    }

    // Forgot password - send OTP
    @PostMapping("/forgot-password")
    public Map<String, String> forgotPassword(
            @RequestBody Map<String, String> request) {

        String message = passwordResetService.generateOtp(
                request.get("email")
        );

        return Map.of("message", message);
    }

    // Verify password reset OTP
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
            @Valid@RequestBody ResetPasswordRequest request) {

        String message = passwordResetService.resetPassword(
                request.getEmail(),
                request.getNewPassword()
        );

        return Map.of("message", message);
    }
}
