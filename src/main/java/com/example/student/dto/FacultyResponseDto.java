package com.example.student.dto;

import lombok.Data;

@Data
public class FacultyResponseDto {

    private Long id;

    private String facultyName;

    private String email;

    private String phone;

    private String department;

    private String designation;

    private String qualification;
}