package com.example.scms.service;

import com.example.scms.dto.CourseResponse;
import com.example.scms.dto.EnrollmentRequest;
import com.example.scms.dto.StudentResponse;

import java.util.List;

public interface EnrollmentService {

    String enrollStudent(EnrollmentRequest enrollmentRequest);
    String unenrollStudent(Long studentId, Long courseId);
    List<CourseResponse> getCoursesByStudentId(Long studentId);
    List<StudentResponse> getStudentsByCourseId(Long courseId);
}
