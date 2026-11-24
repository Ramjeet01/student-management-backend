
package com.example.student.service;

import com.example.student.dto.AttendanceRequestDto;
import com.example.student.dto.AttendanceResponseDto;
import com.example.student.entity.Attendance;
import com.example.student.repository.AttendanceRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Override
    public AttendanceResponseDto addAttendance(AttendanceRequestDto request) {
        Attendance attendance = new Attendance();
        copyRequestToEntity(request, attendance);

        Attendance savedAttendance = attendanceRepository.save(attendance);

        return convertToResponse(savedAttendance);
    }

    @Override
    public List<AttendanceResponseDto> getAllAttendance() {
        return attendanceRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public AttendanceResponseDto getAttendanceById(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Attendance not found with id: " + id));

        return convertToResponse(attendance);
    }

    @Override
    public AttendanceResponseDto updateAttendance(
            Long id, AttendanceRequestDto request) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Attendance not found with id: " + id));

        copyRequestToEntity(request, attendance);

        Attendance updatedAttendance = attendanceRepository.save(attendance);

        return convertToResponse(updatedAttendance);
    }

    @Override
    public void deleteAttendance(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("Attendance not found with id: " + id);
        }

        attendanceRepository.deleteById(id);
    }

    private void copyRequestToEntity(
            AttendanceRequestDto request, Attendance attendance) {

        attendance.setStudentId(request.getStudentId());
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setStatus(request.getStatus());
        attendance.setRemarks(request.getRemarks());
    }

    private AttendanceResponseDto convertToResponse(Attendance attendance) {
        AttendanceResponseDto response = new AttendanceResponseDto();

        response.setId(attendance.getId());
        response.setStudentId(attendance.getStudentId());
        response.setAttendanceDate(attendance.getAttendanceDate());
        response.setStatus(attendance.getStatus());
        response.setRemarks(attendance.getRemarks());

        return response;
    }
}
