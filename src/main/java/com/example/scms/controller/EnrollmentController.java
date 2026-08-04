package com.example.scms.controller;

import com.example.scms.dto.CourseResponse;
import com.example.scms.dto.EnrollmentRequest;
import com.example.scms.dto.EnrollmentResponse;
import com.example.scms.dto.StudentResponse;
import com.example.scms.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<String> enrollStudent(@Valid @RequestBody EnrollmentRequest enrollmentRequest) {
        enrollmentService.enrollStudent(enrollmentRequest);
        return new ResponseEntity<>("Student enrolled successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<String> unenrollStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        enrollmentService.unenrollStudent(studentId, courseId);
        return ResponseEntity.ok("Student unenrolled successfully");
    }

    @GetMapping
    public ResponseEntity<Page<EnrollmentResponse>> getAllEnrollments(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate enrollmentDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<EnrollmentResponse> enrollments = enrollmentService.getAllEnrollments(
                id, studentId, courseId, enrollmentDate, pageable
        );
        return ResponseEntity.ok(enrollments);
    }
}