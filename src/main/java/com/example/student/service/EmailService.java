
package com.example.student.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EmailService {

    @Value("${resend.api-key}")
    private String apiKey;

    @Value("${resend.from-email}")
    private String fromEmail;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.resend.com")
            .build();

    public void sendOtpEmail(String recipientEmail, String otp) {
        String subject = "Student Management System - Password Reset OTP";

        String message =
                "Hello,\n\n" +
                        "Your password reset OTP is: " + otp + "\n\n" +
                        "This OTP is valid for 5 minutes.\n" +
                        "Do not share this OTP with anyone.\n\n" +
                        "Student Management System";

        sendEmail(recipientEmail, subject, message);
    }

    public void sendRegistrationOtpEmail(String recipientEmail, String otp) {
        String subject = "Student Management System - Email Verification";

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

    private void sendEmail(String recipientEmail, String subject, String message) {
        try {
            webClient.post()
                    .uri("/emails")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(java.util.Map.of(
                            "from", fromEmail,
                            "to", new String[]{recipientEmail},
                            "subject", subject,
                            "text", message
                    ))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to send email. Please try again later.", e
            );
        }
    }
}
