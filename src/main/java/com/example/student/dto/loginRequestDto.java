package com.example.student.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class loginRequestDto {

    private String username;
    private String password;
}
