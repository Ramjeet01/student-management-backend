package com.example.student.service;

import com.example.student.dto.requestDto;
import com.example.student.dto.responseDto;
import com.example.student.entity.student;

import java.util.List;

public interface studentService {

    responseDto addStudent(requestDto requestDto);


    List<responseDto> getAllStudents();

    responseDto getStudentById(Long id);

    responseDto updateStudentById(Long id, requestDto requestDto);

    void deleteStudentById(Long id);
}
