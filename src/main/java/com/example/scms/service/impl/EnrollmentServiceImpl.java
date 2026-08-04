package com.example.scms.service.impl;

import com.example.scms.dto.CourseResponse;
import com.example.scms.dto.EnrollmentRequest;
import com.example.scms.dto.EnrollmentResponse;
import com.example.scms.dto.StudentResponse;
import com.example.scms.entity.Course;
import com.example.scms.entity.Enrollment;
import com.example.scms.entity.Student;
import com.example.scms.exception.DuplicateRecordException;
import com.example.scms.exception.ResourceNotFoundException;
import com.example.scms.repository.CourseRepository;
import com.example.scms.repository.EnrollmentRepository;
import com.example.scms.repository.StudentRepository;
import com.example.scms.service.EnrollmentService;
import com.example.scms.specification.EnrollmentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public String enrollStudent(EnrollmentRequest request) {
        log.info("Attempting enrollment - Student ID: {}, Course ID: {}", request.getStudentId(), request.getCourseId());

        // 1. Verify Student exists
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + request.getStudentId()));

        // 2. Verify Course exists
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + request.getCourseId()));

        // 3. Prevent Duplicate Enrollment
        if (enrollmentRepository.existsByStudentIdAndCourseId(request.getStudentId(), request.getCourseId())) {
            log.warn("Enrollment failed - Student ID: {} is already enrolled in Course ID: {}", request.getStudentId(), request.getCourseId());
            throw new DuplicateRecordException("Student is already enrolled in this course.");
        }

        // 4. Create and Save Enrollment Record
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());

        enrollmentRepository.save(enrollment);

        log.info("Student ID: {} successfully enrolled in Course ID: {}", student.getId(), course.getId());
        return "Student successfully enrolled in course.";
    }

    @Override
    @Transactional
    public String unenrollStudent(Long studentId, Long courseId) {
        log.info("Attempting unenrollment - Student ID: {}, Course ID: {}", studentId, courseId);

        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment record not found for Student ID: "
                        + studentId + " and Course ID: " + courseId));

        enrollmentRepository.delete(enrollment);

        log.info("Student ID: {} unenrolled from Course ID: {}", studentId, courseId);
        return "Student successfully unenrolled from course.";
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EnrollmentResponse> getAllEnrollments(
            Long id,
            Long studentId,
            Long courseId,
            LocalDate enrollmentDate,
            Pageable pageable
    ) {
        log.info("Fetching enrollments - id: '{}', studentId: '{}', courseId: '{}', date: '{}', pageable: {}",
                id, studentId, courseId, enrollmentDate, pageable);

        Specification<Enrollment> spec = EnrollmentSpecification.filterEnrollments(id, studentId, courseId, enrollmentDate);

        return enrollmentRepository.findAll(spec, pageable)
                .map(this::mapToResponse);
    }

    private EnrollmentResponse mapToResponse(Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.getId(),
                enrollment.getStudent() != null ? enrollment.getStudent().getId() : null,
                enrollment.getStudent() != null ? enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName() : null,
                enrollment.getCourse() != null ? enrollment.getCourse().getId() : null,
                enrollment.getCourse() != null ? enrollment.getCourse().getCourseName() : null,
                enrollment.getEnrollmentDate()
        );
    }
}