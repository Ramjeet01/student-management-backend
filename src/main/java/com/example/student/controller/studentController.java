
package com.example.student.controller;

import com.example.student.dto.requestDto;
import com.example.student.dto.responseDto;
import com.example.student.service.studentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
public class studentController {

    private final studentService studentService;

    // Create
    @PostMapping
    public responseDto addStudent(@Valid @RequestBody requestDto requestDto) {
        return studentService.addStudent(requestDto);
    }

    // Read All
    @GetMapping
    public List<responseDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Read One
    @GetMapping("/{id}")
    public responseDto getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // Update
    @PutMapping("/{id}")
    public responseDto updateStudentById(
            @PathVariable Long id,
            @Valid @RequestBody requestDto requestDto) {
        return studentService.updateStudentById(id, requestDto);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudentById(id);
    }
}
