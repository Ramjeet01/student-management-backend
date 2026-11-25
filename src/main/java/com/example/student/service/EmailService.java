
package com.example.student.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOtpEmail(String recipientEmail, String otp) {
        String subject =
                "Student Management System - Password Reset OTP";

        String message =
                "Hello,\n\n" +
                        "Your password reset OTP is: " + otp + "\n\n" +
                        "This OTP is valid for 5 minutes.\n" +
                        "Do not share this OTP with anyone.\n\n" +
                        "If you did not request a password reset, please ignore this email.\n\n" +
                        "Student Management System";

        sendEmail(recipientEmail, subject, message);
    }

    public void sendRegistrationOtpEmail(String recipientEmail, String otp) {
        String subject =
                "Student Management System - Email Verification";

        String message =
                "Hello,\n\n" +
                        "Thank you for registering with Student Management System.\n\n" +
                        "Your email verification OTP is: " + otp + "\n\n" +
                        "This OTP is valid for 5 minutes.\n" +
                        "Do not share this OTP with anyone.\n\n" +
                        "Please enter this OTP to complete your registration.\n\n" +
                        "Student Management System";

        sendEmail(recipientEmail, subject, message);
    }

    private void sendEmail(
            String recipientEmail,
            String subject,
            String message
    ) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setTo(recipientEmail);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);

            mailSender.send(mailMessage);

            log.info("OTP email sent successfully to {}", recipientEmail);

        } catch (Exception e) {
            log.error("Gmail SMTP failed while sending email to {}", recipientEmail, e);

            throw new RuntimeException(
                    "Unable to send email. Please try again later.", e
            );
        }
    }
}
