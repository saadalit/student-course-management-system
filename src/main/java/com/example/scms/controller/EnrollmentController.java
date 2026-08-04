package com.example.scms.controller;

import com.example.scms.dto.CourseResponse;
import com.example.scms.dto.EnrollmentRequest;
import com.example.scms.dto.StudentResponse;
import com.example.scms.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return new ResponseEntity<>("Student unenrolled successfully", HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}/course")
    public ResponseEntity<List<CourseResponse>> getCoursesByStudentID(@PathVariable Long studentId) {
        List<CourseResponse> courses = enrollmentService.getCoursesByStudentId(studentId);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}/student")
    public ResponseEntity<List<StudentResponse>> getStudentsByCourseID(@PathVariable Long courseId)
    {
        List<StudentResponse> students = enrollmentService.getStudentsByCourseId(courseId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

}