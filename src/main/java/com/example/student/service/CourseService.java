
package com.example.student.service;

import com.example.student.dto.CourseRequestDto;
import com.example.student.dto.CourseResponseDto;

import java.util.List;

public interface CourseService {

    CourseResponseDto addCourse(CourseRequestDto request);

    List<CourseResponseDto> getAllCourses();

    CourseResponseDto getCourseById(Long id);

    CourseResponseDto updateCourse(Long id, CourseRequestDto request);

    void deleteCourse(Long id);

    long getCourseCount();
}
