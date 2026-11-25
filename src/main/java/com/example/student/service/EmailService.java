
package com.example.student.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Value("${resend.api-key}")
    private String apiKey;

    @Value("${resend.from-email}")
    private String senderEmail;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    // Password reset OTP
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

    // Registration verification OTP
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

    // Common Resend email method
    private void sendEmail(
            String recipientEmail,
            String subject,
            String message
    ) {

        String jsonBody = """
                {
                  "from": "%s",
                  "to": ["%s"],
                  "subject": "%s",
                  "text": "%s"
                }
                """.formatted(
                escapeJson(senderEmail),
                escapeJson(recipientEmail),
                escapeJson(subject),
                escapeJson(message)
        );

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.resend.com/emails"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            jsonBody,
                            StandardCharsets.UTF_8
                    ))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() < 200 ||
                    response.statusCode() >= 300) {

                throw new RuntimeException(
                        "Resend email failed: " + response.body()
                );
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException(
                    "Email sending was interrupted.", e
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to send email: " + e.getMessage(), e
            );
        }
    }

    private String escapeJson(String value) {

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
