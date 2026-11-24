
package com.example.student.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AttendanceRequestDto {

    private Long studentId;
    private LocalDate attendanceDate;
    private String status;
    private String remarks;
}
