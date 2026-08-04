package com.example.scms.service;

import com.example.scms.dto.StudentRequest;
import com.example.scms.dto.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;

public interface StudentService {

    StudentResponse createStudent(StudentRequest studentRequest);

    // Updated to handle Search, Filter, Pagination & Sorting in one clean service contract
    Page<StudentResponse> getAllStudents(
            Long id,
            String studentCode,
            String firstName,
            String lastName,
            String email,
            String phone,
            String gender,
            String status,
            Pageable pageable
    );
    StudentResponse getStudentById(Long id);

    StudentResponse updateStudent(Long id, StudentRequest studentRequest);

    void deleteStudent(Long id);

}