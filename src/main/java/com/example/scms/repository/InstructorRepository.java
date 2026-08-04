package com.example.scms.repository;

import com.example.scms.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> , JpaSpecificationExecutor<Instructor> {

    Optional<Instructor> findByEmail(String email);
    Boolean existsByEmail(String email);
}
