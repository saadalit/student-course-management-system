package com.example.scms.service.impl;

import com.example.scms.dto.StudentRequest;
import com.example.scms.dto.StudentResponse;
import com.example.scms.entity.Student;
import com.example.scms.entity.enums.StudentStatus;
import com.example.scms.entity.enums.UserRole;
import com.example.scms.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentServiceImpl;

    private Student student;
    private StudentRequest studentRequest;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setStudentCode("STU001");
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");
        student.setPhone("1234567890");
        student.setGender("MALE");
        student.setStatus(StudentStatus.ACTIVE);

        studentRequest = new StudentRequest();
        studentRequest.setStudentCode("STU001");
        studentRequest.setFirstName("John");
        studentRequest.setLastName("Doe");
        studentRequest.setEmail("john.doe@example.com");
        studentRequest.setPhone("1234567890");
        studentRequest.setGender("MALE");
        studentRequest.setStatus(StudentStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should successfully create a new student")
    void testCreateStudent_Success() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentResponse response = studentServiceImpl.createStudent(studentRequest);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getStudentCode()).isEqualTo("STU001");
        assertThat(response.getFirstName()).isEqualTo("John");


        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    @DisplayName("Should return student response when student exists")
    void testGetStudentById_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        StudentResponse response = studentServiceImpl.getStudentById(1L);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getStudentCode()).isEqualTo("STU001");

        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when student does not exist")
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> studentServiceImpl.getStudentById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Student not found");

        verify(studentRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should successfully update an existing student")
    void testUpdateStudent_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        studentRequest.setFirstName("Jane");
        StudentResponse response = studentServiceImpl.updateStudent(1L, studentRequest);
        assertThat(response).isNotNull();
        assertThat(response.getFirstName()).isEqualTo("Jane");
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    @DisplayName("Should throw exception when updating a non-existing student")
    void testUpdateStudent_NotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> studentServiceImpl.updateStudent(99L, studentRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Student not found");

        verify(studentRepository, times(1)).findById(99L);
        verify(studentRepository, times(0)).save(any(Student.class));
    }

    @Test
    @DisplayName("Should successfully delete an existing student")
    void testDeleteStudent_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        studentServiceImpl.deleteStudent(1L);

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    @DisplayName("Should throw exception when deleting a non-existing student")
    void testDeleteStudent_NotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> studentServiceImpl.deleteStudent(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Student not found");

        verify(studentRepository, times(1)).findById(99L);
        verify(studentRepository, times(0)).delete(any(Student.class));
    }
}
