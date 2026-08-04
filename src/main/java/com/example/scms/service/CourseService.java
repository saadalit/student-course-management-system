package com.example.scms.service;

import com.example.scms.dto.CourseRequest;
import com.example.scms.dto.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    CourseResponse createCourse(CourseRequest courseRequest);

    // Consolidated method to handle Search, Filter, Pagination & Sorting
    Page<CourseResponse> getAllCourses(
            Long id,
            String courseName,
            String courseCode,
            String department,
            String semester,
            Integer creditHours,
            Pageable pageable
    );
    CourseResponse getCourseById(Long id);

    CourseResponse updateCourse(Long id, CourseRequest courseRequest);

    void deleteCourse(Long id);
}