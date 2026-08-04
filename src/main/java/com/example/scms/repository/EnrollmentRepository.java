package com.example.scms.repository;

import com.example.scms.entity.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> , JpaSpecificationExecutor<Enrollment> {

    // Check if duplicate enrollment exists
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    // Find specific enrollment for deletion
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);

    // Get all enrollments for a student
    Page<Enrollment> findByStudentId(Long studentId, Pageable pageable);

    // Get all enrollments for a course
    Page<Enrollment> findByCourseId(Long courseId, Pageable pageable);
}