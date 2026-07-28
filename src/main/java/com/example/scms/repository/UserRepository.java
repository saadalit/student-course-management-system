package com.example.scms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.scms.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

}
