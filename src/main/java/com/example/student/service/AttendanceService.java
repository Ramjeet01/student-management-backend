
package com.example.student.service;

import com.example.student.dto.AttendanceRequestDto;
import com.example.student.dto.AttendanceResponseDto;

import java.util.List;

public interface AttendanceService {

    AttendanceResponseDto addAttendance(AttendanceRequestDto request);

    List<AttendanceResponseDto> getAllAttendance();

    AttendanceResponseDto getAttendanceById(Long id);

    AttendanceResponseDto updateAttendance(Long id, AttendanceRequestDto request);

    void deleteAttendance(Long id);

}
