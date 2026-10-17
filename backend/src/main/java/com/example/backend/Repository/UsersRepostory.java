package com.example.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.model.Users;
import java.util.Optional;

public interface UsersRepostory
        extends JpaRepository<Users, Long> {
    Optional<Users> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
