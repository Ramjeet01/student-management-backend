package com.example.student.service;

import com.example.student.dto.requestDto;
import com.example.student.dto.responseDto;
import com.example.student.entity.student;
import com.example.student.repository.studentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class studentServiceImpl implements studentService {

    public final studentRepository studentRepository;

    // =========================
    // CREATE
    // =========================

    @Override
    public responseDto addStudent(requestDto requestDto) {

        student student = new student();

        student.setName(requestDto.getName());
        student.setClasses(requestDto.getClasses());
        student.setSection(requestDto.getSection());
        student.setSubject(requestDto.getSubject());

        student savedStudent = studentRepository.save(student);

        responseDto responseDto = new responseDto();

        responseDto.setId(savedStudent.getId());
        responseDto.setName(savedStudent.getName());
        responseDto.setClasses(savedStudent.getClasses());
        responseDto.setSection(savedStudent.getSection());
        responseDto.setSubject(savedStudent.getSubject());

        return responseDto;
    }


    // =========================
    // READ ALL
    // =========================

    @Override
    public List<responseDto> getAllStudents() {

        List<student> students = studentRepository.findAll();

        List<responseDto> responseList = new ArrayList<>();

        for (student student : students) {

            responseDto responseDto = new responseDto();

            responseDto.setId(student.getId());
            responseDto.setName(student.getName());
            responseDto.setClasses(student.getClasses());
            responseDto.setSection(student.getSection());
            responseDto.setSubject(student.getSubject());

            responseList.add(responseDto);
        }

        return responseList;
    }


    // =========================
    // READ ONE
    // =========================

    @Override
    public responseDto getStudentById(Long id) {

        student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with id: " + id
                        )
                );

        responseDto responseDto = new responseDto();

        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setClasses(student.getClasses());
        responseDto.setSection(student.getSection());
        responseDto.setSubject(student.getSubject());

        return responseDto;
    }


    // =========================
    // UPDATE
    // =========================

    @Override
    public responseDto updateStudentById(
            Long id,
            requestDto requestDto) {

        student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with id: " + id
                        )
                );

        student.setName(requestDto.getName());
        student.setClasses(requestDto.getClasses());
        student.setSection(requestDto.getSection());
        student.setSubject(requestDto.getSubject());

        student updatedStudent = studentRepository.save(student);

        responseDto responseDto = new responseDto();

        responseDto.setId(updatedStudent.getId());
        responseDto.setName(updatedStudent.getName());
        responseDto.setClasses(updatedStudent.getClasses());
        responseDto.setSection(updatedStudent.getSection());
        responseDto.setSubject(updatedStudent.getSubject());

        return responseDto;
    }


    // =========================
    // DELETE
    // =========================

    @Override
    public void deleteStudentById(Long id) {

        student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with id: " + id
                        )
                );

        studentRepository.delete(student);


    }
}
