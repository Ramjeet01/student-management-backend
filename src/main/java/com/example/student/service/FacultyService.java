package com.example.student.service;

import com.example.student.dto.FacultyRequestDto;
import com.example.student.dto.FacultyResponseDto;

import java.util.List;

public interface FacultyService {

    FacultyResponseDto addFaculty(FacultyRequestDto request);

    List<FacultyResponseDto> getAllFaculty();

    FacultyResponseDto getFacultyById(Long id);

    FacultyResponseDto updateFaculty(Long id, FacultyRequestDto request);

    void deleteFaculty(Long id);

    long getFacultyCount();
}