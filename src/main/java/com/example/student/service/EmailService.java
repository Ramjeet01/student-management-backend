
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

    public void sendOtpEmail(String recipientEmail, String otp) {

        String jsonBody = """
                {
                  "from": "%s",
                  "to": ["%s"],
                  "subject": "Student Management System - Password Reset OTP",
                  "text": "Hello,\\n\\nYour password reset OTP is: %s\\n\\nThis OTP is valid for 5 minutes.\\nDo not share this OTP with anyone.\\n\\nIf you did not request a password reset, please ignore this email.\\n\\nStudent Management System"
                }
                """.formatted(senderEmail, recipientEmail, otp);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.resend.com/emails"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            jsonBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new RuntimeException(
                        "Resend email failed: " + response.body()
                );
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Email sending was interrupted.", e);

        } catch (Exception e) {
            throw new RuntimeException("Unable to send OTP email: " + e.getMessage(), e);
        }
    }
}
