package com.example.scms.repository;

import com.example.scms.entity.Student;
import com.example.scms.entity.enums.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentCode(String studentCode);
    Optional<Student> findByEmail(String email);
    Boolean existsByStudentCode(String studentCode);
    Boolean existsByEmail(String email);
    List<Student> findByFirstNameIgnoreCaseOrLastNameIgnoreCase(String firstName, String lastName);
    List<Student> findByStatus(StudentStatus status);
}
