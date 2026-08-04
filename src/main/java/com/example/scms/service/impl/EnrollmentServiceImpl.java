package com.example.scms.service.impl;

import com.example.scms.dto.CourseResponse;
import com.example.scms.dto.EnrollmentRequest;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<CourseResponse> getCoursesByStudentId(Long studentId) {
        log.info("Fetching courses for Student ID: {}", studentId);

        // Verify Student exists
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
        }

        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);

        return enrollments.stream()
                .map(enrollment -> {
                    Course course = enrollment.getCourse();
                    return new CourseResponse(
                            course.getId(),
                            course.getCourseCode(),
                            course.getCourseName(),
                            course.getCreditHours(),
                            course.getDepartment(),
                            course.getSemester()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsByCourseId(Long courseId) {
        log.info("Fetching students for Course ID: {}", courseId);

        // Verify Course exists
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found with ID: " + courseId);
        }

        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);

        return enrollments.stream()
                .map(enrollment -> {
                    Student student = enrollment.getStudent();
                    return new StudentResponse(
                            student.getId(),
                            student.getStudentCode(),
                            student.getFirstName(),
                            student.getLastName(),
                            student.getEmail(),
                            student.getPhone(),
                            student.getDateOfBirth(),
                            student.getGender(),
                            student.getStatus()
                    );
                })
                .collect(Collectors.toList());
    }
}