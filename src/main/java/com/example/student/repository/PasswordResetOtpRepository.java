
package com.example.student.repository;

import com.example.student.entity.PasswordResetOtp;
import com.example.student.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetOtpRepository
        extends JpaRepository<PasswordResetOtp, Long> {

    Optional<PasswordResetOtp> findByUser(User user);

    void deleteByUser(User user);
}
