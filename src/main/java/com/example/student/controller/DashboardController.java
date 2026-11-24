
package com.example.student.controller;

import com.example.student.repository.studentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class DashboardController {

    private final studentRepository studentRepository;

    @GetMapping("/stats")
    public Map<String, Long> getDashboardStats() {

        long totalStudents = studentRepository.count();

        return Map.of(
                "totalStudents", totalStudents
        );
    }
}
