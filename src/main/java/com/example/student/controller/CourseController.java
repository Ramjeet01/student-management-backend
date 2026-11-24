
package com.example.student.controller;

import com.example.student.dto.CourseRequestDto;
import com.example.student.dto.CourseResponseDto;
import com.example.student.service.CourseService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponseDto addCourse(
            @RequestBody CourseRequestDto request) {

        return courseService.addCourse(request);
    }

    // READ ALL
    @GetMapping
    public List<CourseResponseDto> getAllCourses() {

        return courseService.getAllCourses();
    }

    // READ ONE
    @GetMapping("/{id}")
    public CourseResponseDto getCourseById(
            @PathVariable Long id) {

        return courseService.getCourseById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public CourseResponseDto updateCourse(
            @PathVariable Long id,
            @RequestBody CourseRequestDto request) {

        return courseService.updateCourse(id, request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable Long id) {

        courseService.deleteCourse(id);
    }

    // COUNT
    @GetMapping("/count")
    public Map<String, Long> getCourseCount() {

        return Map.of(
                "totalCourses",
                courseService.getCourseCount()
        );
    }
}
