package com.example.student.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class registerRequestDto {

    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String password;
}
