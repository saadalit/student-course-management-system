package com.example.scms.service.impl;

import com.example.scms.dto.CourseRequest;
import com.example.scms.dto.CourseResponse;
import com.example.scms.entity.Course;
import com.example.scms.exception.DuplicateRecordException;
import com.example.scms.exception.ResourceNotFoundException;
import com.example.scms.repository.CourseRepository;
import com.example.scms.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public CourseResponse createCourse(CourseRequest courseRequest) {
        log.info("Creating a new course with code: {}", courseRequest.getCourseCode());

        if(courseRepository.existsByCourseCode(courseRequest.getCourseCode())){
            log.error("Course with code {} already exists", courseRequest.getCourseCode());
            throw new DuplicateRecordException("Course with this code already exists");
        }

        Course course = mapToEntity(courseRequest);
        Course savedCourse = courseRepository.save(course);
        log.info("Course created successfully with ID: {}", savedCourse.getId());
        return mapToResponse(savedCourse);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        log.info("Fetching all courses");
        return courseRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        log.info("Fetching course with ID: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Course with ID {} not found", id);
                    return new ResourceNotFoundException("Course not found");
                });
        return mapToResponse(course);
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseRequest courseRequest) {
        log.info("Updating course with ID: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Course with ID {} not found", id);
                    return new ResourceNotFoundException("Course not found");
                });
        if (!course.getCourseCode().equals(courseRequest.getCourseCode()) && courseRepository.existsByCourseCode(courseRequest.getCourseCode())) {
            log.error("Course with code {} already exists", courseRequest.getCourseCode());
            throw new DuplicateRecordException("Course with this code already exists");
        }
        course.setCourseCode(courseRequest.getCourseCode());
        course.setCourseName(courseRequest.getCourseName());
        course.setCreditHours(courseRequest.getCreditHours());
        course.setDepartment(courseRequest.getDepartment());
        course.setSemester(courseRequest.getSemester());
        Course updatedCourse = courseRepository.save(course);
        log.info("Course updated successfully with ID: {}", updatedCourse.getId());
        return mapToResponse(updatedCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        log.info("Deleting course with ID: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Course with ID {} not found", id);
                    return new ResourceNotFoundException("Course not found");
                });
        courseRepository.delete(course);
        log.info("Course deleted successfully with ID: {}", id);
    }

    @Override
    public List<CourseResponse> searchCoursesByDepartment(String department) {
        log.info("Searching courses in department: {}", department);
        return courseRepository.findByDepartmentIgnoreCase(department).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CourseResponse mapToResponse(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getCourseCode(),
                course.getCourseName(),
                course.getCreditHours(),
                course.getDepartment(),
                course.getSemester()
        );
    }

    private Course mapToEntity(CourseRequest courseRequest) {
        Course course = new Course();
        course.setCourseCode(courseRequest.getCourseCode());
        course.setCourseName(courseRequest.getCourseName());
        course.setCreditHours(courseRequest.getCreditHours());
        course.setDepartment(courseRequest.getDepartment());
        course.setSemester(courseRequest.getSemester());
        return course;
    }

}
