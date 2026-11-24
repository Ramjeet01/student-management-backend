package com.example.student.controller;

import com.example.student.repository.studentRepository;
import com.example.student.repository.CourseRepository;
import com.example.student.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {
        "http://localhost:5173"
})
@RequiredArgsConstructor
public class DashboardController {

    private final studentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final FacultyRepository facultyRepository;

    @GetMapping("/stats")
    public Map<String, Long> getDashboardStats() {

        long totalStudents = studentRepository.count();
        long totalCourses = courseRepository.count();
        long totalFaculty = facultyRepository.count();

        return Map.of(
                "totalStudents", totalStudents,
                "totalCourses", totalCourses,
                "totalFaculty", totalFaculty
        );
    }
}