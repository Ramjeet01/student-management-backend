package com.example.student.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "faculty")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String facultyName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private String department;

    private String designation;

    @Column(length = 1000)
    private String qualification;
}