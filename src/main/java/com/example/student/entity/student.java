package com.example.student.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "students")
public class student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String classes;
    private String section;
    private String subject;
}
