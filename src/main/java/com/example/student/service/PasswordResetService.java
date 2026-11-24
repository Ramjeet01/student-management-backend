
package com.example.student.service;

import com.example.student.entity.PasswordResetOtp;
import com.example.student.entity.User;
import com.example.student.repository.PasswordResetOtpRepository;
import com.example.student.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetOtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final SecureRandom secureRandom = new SecureRandom();

    // Generate and send OTP
    @Transactional
    public String generateOtp(String email) {

        User user = userRepository.findByEmail(email)
                .orElse(null);

        // Do not reveal whether an email is registered
        if (user == null) {
            return "If the email is registered, an OTP will be sent.";
        }

        otpRepository.deleteByUser(user);

        String otp = String.format(
                "%06d",
                secureRandom.nextInt(1000000)
        );

        PasswordResetOtp resetOtp = new PasswordResetOtp();

        resetOtp.setUser(user);
        resetOtp.setOtpHash(passwordEncoder.encode(otp));
        resetOtp.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        resetOtp.setAttempts(0);
        resetOtp.setVerified(false);

        otpRepository.save(resetOtp);

        emailService.sendOtpEmail(email, otp);

        return "If the email is registered, an OTP will be sent.";
    }

    // Verify OTP
    @Transactional(noRollbackFor = RuntimeException.class)
    public String verifyOtp(String email, String otp) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Invalid or expired OTP.")
                );

        PasswordResetOtp resetOtp = otpRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Invalid or expired OTP.")
                );

        if (resetOtp.isVerified()) {
            throw new RuntimeException("OTP already verified.");
        }

        if (resetOtp.getExpiresAt().isBefore(LocalDateTime.now())) {
            otpRepository.delete(resetOtp);
            throw new RuntimeException("OTP has expired.");
        }

        if (resetOtp.getAttempts() >= 5) {
            otpRepository.delete(resetOtp);
            throw new RuntimeException("Maximum OTP attempts exceeded.");
        }

        if (!passwordEncoder.matches(otp, resetOtp.getOtpHash())) {

            resetOtp.setAttempts(resetOtp.getAttempts() + 1);

            otpRepository.save(resetOtp);

            throw new RuntimeException("Incorrect OTP.");
        }

        resetOtp.setVerified(true);
        otpRepository.save(resetOtp);

        return "OTP verified successfully.";


    }


    // Reset password after successful OTP verification
    @Transactional
    public String resetPassword(String email, String newPassword) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found.")
                );

        PasswordResetOtp resetOtp = otpRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Please verify your OTP first.")
                );

        if (!resetOtp.isVerified()) {
            throw new RuntimeException("Please verify your OTP first.");
        }

        if (resetOtp.getExpiresAt().isBefore(LocalDateTime.now())) {
            otpRepository.delete(resetOtp);
            throw new RuntimeException("OTP verification has expired.");
        }

        if (newPassword == null || newPassword.length() < 8) {
            throw new RuntimeException(
                    "Password must contain at least 8 characters."
            );
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        // OTP can no longer be reused
        otpRepository.delete(resetOtp);

        return "Password reset successfully.";
    }

}
