
package com.example.student.repository;

import com.example.student.entity.PendingRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PendingRegistrationRepository
        extends JpaRepository<PendingRegistration, Long> {

    Optional<PendingRegistration> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    void deleteByEmail(String email);
}
