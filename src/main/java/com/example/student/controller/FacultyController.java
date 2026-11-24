package com.example.student.controller;

import com.example.student.dto.FacultyRequestDto;
import com.example.student.dto.FacultyResponseDto;
import com.example.student.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/faculty")
@CrossOrigin(origins = {
        "http://localhost:5173"
})
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @PostMapping
    public ResponseEntity<FacultyResponseDto> addFaculty(
            @RequestBody FacultyRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(facultyService.addFaculty(request));
    }

    @GetMapping
    public ResponseEntity<List<FacultyResponseDto>> getAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyResponseDto> getFacultyById(
            @PathVariable Long id) {

        return ResponseEntity.ok(facultyService.getFacultyById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacultyResponseDto> updateFaculty(
            @PathVariable Long id,
            @RequestBody FacultyRequestDto request) {

        return ResponseEntity.ok(
                facultyService.updateFaculty(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getFacultyCount() {
        return ResponseEntity.ok(
                Map.of("totalFaculty", facultyService.getFacultyCount()));
    }
}