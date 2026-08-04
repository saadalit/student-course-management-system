package com.example.scms.service;

import com.example.scms.dto.CourseResponse;
import com.example.scms.dto.EnrollmentRequest;
import com.example.scms.dto.EnrollmentResponse;
import com.example.scms.dto.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface EnrollmentService {

    String enrollStudent(EnrollmentRequest enrollmentRequest);

    String unenrollStudent(Long studentId, Long courseId);

    Page<EnrollmentResponse> getAllEnrollments(
            Long id,
            Long studentId,
            Long courseId,
            LocalDate enrollmentDate,
            Pageable pageable
    );
}