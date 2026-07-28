package com.example.scms.repository;

import com.example.scms.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseCode(String courseCode);
    Boolean existsByCourseCode(String courseCode);
    List<Course> findByDepartmentIgnoreCase(String department);
}
