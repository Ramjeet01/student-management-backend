package com.example.student.service;

import com.example.student.dto.FacultyRequestDto;
import com.example.student.dto.FacultyResponseDto;
import com.example.student.entity.Faculty;
import com.example.student.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    @Override
    public FacultyResponseDto addFaculty(FacultyRequestDto request) {
        Faculty faculty = new Faculty();
        copyRequestToEntity(request, faculty);

        Faculty savedFaculty = facultyRepository.save(faculty);

        return convertToResponse(savedFaculty);
    }

    @Override
    public List<FacultyResponseDto> getAllFaculty() {
        return facultyRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public FacultyResponseDto getFacultyById(Long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Faculty not found with id: " + id));

        return convertToResponse(faculty);
    }

    @Override
    public FacultyResponseDto updateFaculty(Long id, FacultyRequestDto request) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Faculty not found with id: " + id));

        copyRequestToEntity(request, faculty);

        Faculty updatedFaculty = facultyRepository.save(faculty);

        return convertToResponse(updatedFaculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        if (!facultyRepository.existsById(id)) {
            throw new RuntimeException("Faculty not found with id: " + id);
        }

        facultyRepository.deleteById(id);
    }

    @Override
    public long getFacultyCount() {
        return facultyRepository.count();
    }

    private void copyRequestToEntity(FacultyRequestDto request, Faculty faculty) {
        faculty.setFacultyName(request.getFacultyName());
        faculty.setEmail(request.getEmail());
        faculty.setPhone(request.getPhone());
        faculty.setDepartment(request.getDepartment());
        faculty.setDesignation(request.getDesignation());
        faculty.setQualification(request.getQualification());
    }

    private FacultyResponseDto convertToResponse(Faculty faculty) {
        FacultyResponseDto response = new FacultyResponseDto();

        response.setId(faculty.getId());
        response.setFacultyName(faculty.getFacultyName());
        response.setEmail(faculty.getEmail());
        response.setPhone(faculty.getPhone());
        response.setDepartment(faculty.getDepartment());
        response.setDesignation(faculty.getDesignation());
        response.setQualification(faculty.getQualification());

        return response;
    }
}