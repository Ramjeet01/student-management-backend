package com.example.student.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Getter
@Setter

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;
    private boolean enabled = true;
    private String role = "ROLE_USER";

}
