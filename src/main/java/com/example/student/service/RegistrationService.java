
package com.example.student.service;

import com.example.student.dto.registerRequestDto;
import com.example.student.entity.PendingRegistration;
import com.example.student.entity.User;
import com.example.student.repository.PendingRegistrationRepository;
import com.example.student.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PendingRegistrationRepository pendingRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final SecureRandom secureRandom = new SecureRandom();

    // Generate and send registration OTP
    @Transactional
    public String startRegistration(registerRequestDto request) {

        String username = request.getUsername().trim();
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email is already registered.");
        }

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username is already taken.");
        }

        // Remove previous pending registration for this email
        pendingRepository.deleteByEmail(email);

        if (pendingRepository.existsByUsername(username)) {
            throw new RuntimeException("Username is already reserved.");
        }

        String otp = String.format(
                "%06d",
                secureRandom.nextInt(1000000)
        );

        PendingRegistration pending = new PendingRegistration();

        pending.setUsername(username);
        pending.setEmail(email);
        pending.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );
        pending.setOtpHash(passwordEncoder.encode(otp));
        pending.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        pending.setAttempts(0);

        pendingRepository.save(pending);

        emailService.sendRegistrationOtpEmail(email, otp);

        return "Registration OTP sent successfully. Please verify your email.";
    }

    // Verify OTP and create actual user account
    @Transactional(noRollbackFor = RuntimeException.class)
    public String verifyRegistration(String email, String otp) {

        email = email.trim().toLowerCase();

        PendingRegistration pending = pendingRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Registration not found. Please register again.")
                );

        if (pending.getExpiresAt().isBefore(LocalDateTime.now())) {
            pendingRepository.delete(pending);
            throw new RuntimeException("OTP has expired. Please register again.");
        }

        if (pending.getAttempts() >= 5) {
            pendingRepository.delete(pending);
            throw new RuntimeException("Maximum OTP attempts exceeded.");
        }

        if (!passwordEncoder.matches(otp, pending.getOtpHash())) {

            pending.setAttempts(pending.getAttempts() + 1);
            pendingRepository.save(pending);

            throw new RuntimeException("Incorrect OTP. Please try again.");
        }

        if (userRepository.existsByEmail(email)) {
            pendingRepository.delete(pending);
            throw new RuntimeException("Email is already registered.");
        }

        if (userRepository.existsByUsername(pending.getUsername())) {
            pendingRepository.delete(pending);
            throw new RuntimeException("Username is already taken.");
        }

        User user = new User();

        user.setUsername(pending.getUsername());
        user.setEmail(pending.getEmail());
        user.setPassword(pending.getPasswordHash());
        user.setEnabled(true);

        // Public registration cannot create administrators
        user.setRole("ROLE_USER");

        userRepository.save(user);

        pendingRepository.delete(pending);

        return "Email verified and registration completed successfully.";
    }
}
