package com.example.scms.service;

import com.example.scms.dto.StudentRequest;
import com.example.scms.dto.StudentResponse;
import com.example.scms.entity.enums.UserRole;

import java.util.List;

public interface StudentService {

    StudentResponse createStudent(StudentRequest studentRequest);
    List<StudentResponse> getAllStudents();
    StudentResponse getStudentById(Long id);
    StudentResponse updateStudent(Long id, StudentRequest studentRequest);
    void deleteStudent(Long id);
    List<StudentResponse> searchStudentsByName(String name);
}
