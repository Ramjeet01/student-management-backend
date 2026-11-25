
package com.example.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class requestDto {

    @NotBlank(message = "Student name is required")
    private String name;

    @NotBlank(message = "Class is required")
    @Pattern(
            regexp = "B\\.Tech|BCA|B\\.Sc|BA",
            message = "B.Tech, BCA, B.Sc, or BA"
    )
    private String classes;

    @NotBlank(message = "Section is required")
    @Pattern(
            regexp = "A|B|C|D",
            message = "A, B, C, or D"
    )
    private String section;

    @NotBlank(message = "Subject is required")
    @Pattern(
            regexp = "IIOT|Deep Learning|Machine Learning|Artificial Intelligence|Cloud Computing|Data Science|Computer Networks",
            message = "Please enter a valid subject"
    )
    private String subject;
}
