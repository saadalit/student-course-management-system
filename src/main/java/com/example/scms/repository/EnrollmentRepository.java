package com.example.scms.repository;

import com.example.scms.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // Check if duplicate enrollment exists
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    // Find specific enrollment for deletion
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);

    // Get all enrollments for a student
    List<Enrollment> findByStudentId(Long studentId);

    // Get all enrollments for a course
    List<Enrollment> findByCourseId(Long courseId);
}