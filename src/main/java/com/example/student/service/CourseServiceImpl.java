
package com.example.student.service;

import com.example.student.dto.CourseRequestDto;
import com.example.student.dto.CourseResponseDto;
import com.example.student.entity.Course;
import com.example.student.repository.CourseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    // CREATE
    @Override
    public CourseResponseDto addCourse(CourseRequestDto request) {

        Course course = new Course();
        course.setCourseName(request.getCourseName());
        course.setCourseCode(request.getCourseCode());
        course.setDuration(request.getDuration());
        course.setDescription(request.getDescription());

        return convertToResponse(courseRepository.save(course));
    }

    // READ ALL
    @Override
    public List<CourseResponseDto> getAllCourses() {

        return courseRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // READ ONE
    @Override
    public CourseResponseDto getCourseById(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found: " + id));

        return convertToResponse(course);
    }

    // UPDATE
    @Override
    public CourseResponseDto updateCourse(
            Long id, CourseRequestDto request) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found: " + id));

        course.setCourseName(request.getCourseName());
        course.setCourseCode(request.getCourseCode());
        course.setDuration(request.getDuration());
        course.setDescription(request.getDescription());

        return convertToResponse(courseRepository.save(course));
    }

    // DELETE
    @Override
    public void deleteCourse(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found: " + id));

        courseRepository.delete(course);
    }

    // COUNT
    @Override
    public long getCourseCount() {
        return courseRepository.count();
    }

    // ENTITY TO RESPONSE DTO
    private CourseResponseDto convertToResponse(Course course) {

        CourseResponseDto response = new CourseResponseDto();

        response.setId(course.getId());
        response.setCourseName(course.getCourseName());
        response.setCourseCode(course.getCourseCode());
        response.setDuration(course.getDuration());
        response.setDescription(course.getDescription());

        return response;
    }
}
