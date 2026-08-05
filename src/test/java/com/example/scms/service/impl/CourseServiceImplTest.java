package com.example.scms.service.impl;

import com.example.scms.dto.CourseRequest;
import com.example.scms.dto.CourseResponse;
import com.example.scms.entity.Course;
import com.example.scms.entity.Instructor;
import com.example.scms.repository.CourseRepository;
import com.example.scms.repository.InstructorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;
    private CourseRequest courseRequest;

    @BeforeEach
    void setUp() {

        course = new Course();
        course.setId(1L);
        course.setCourseName("Data Structures");
        course.setCourseCode("CS201");
        course.setDepartment("Computer Science");
        course.setSemester("FALL_2026");
        course.setCreditHours(3);


        courseRequest = new CourseRequest();
        courseRequest.setCourseName("Data Structures");
        courseRequest.setCourseCode("CS201");
        courseRequest.setDepartment("Computer Science");
        courseRequest.setSemester("FALL_2026");
        courseRequest.setCreditHours(3);
    }

    @Test
    @DisplayName("Should create a new course successfully")
    void testCreateCourse() {
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        CourseResponse response = courseService.createCourse(courseRequest);

        assertThat(response).isNotNull();
        assertThat(response.getCourseName()).isEqualTo("Data Structures");
        assertThat(response.getCourseCode()).isEqualTo("CS201");

        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    @DisplayName("Should return paged list of courses based on filters")
    void testGetAllCourses() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Course> coursePage = new PageImpl<>(List.of(course), pageable, 1);
        when(courseRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(coursePage);

        Page<CourseResponse> responsePage = courseService.getAllCourses(
                1L, "Data Structures", "CS201", "Computer Science", "FALL_2026", 3, pageable
        );

        assertThat(responsePage).isNotNull();
        assertThat(responsePage.getTotalElements()).isEqualTo(1);
        assertThat(responsePage.getContent().get(0).getCourseName()).isEqualTo("Data Structures");
        assertThat(responsePage.getContent().get(0).getCourseCode()).isEqualTo("CS201");
        verify(courseRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    @DisplayName("Should delete a course successfully")
    void testDeleteCourse() {
        // Return Optional.of(course) for findById
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        doNothing().when(courseRepository).delete(course);

        courseService.deleteCourse(1L);

        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).delete(course);
    }
}
