package com.example.scms.service;

import com.example.scms.dto.CourseRequest;
import com.example.scms.dto.CourseResponse;
import com.example.scms.entity.enums.UserRole;

import java.util.List;

public interface CourseService {

    CourseResponse createCourse(CourseRequest courseRequest);
    List<CourseResponse> getAllCourses();
    CourseResponse getCourseById(Long id);
    CourseResponse updateCourse(Long id, CourseRequest courseRequest);
    void deleteCourse(Long id);
    List<CourseResponse> searchCoursesByDepartment(String name);
}
