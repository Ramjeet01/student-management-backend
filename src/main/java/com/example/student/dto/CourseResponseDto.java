
package com.example.student.dto;

import lombok.Data;

@Data
public class CourseResponseDto {

    private Long id;

    private String courseName;

    private String courseCode;

    private String duration;

    private String description;
}
