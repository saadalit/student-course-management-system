package com.example.scms.service;

import com.example.scms.dto.InstructorRequest;
import com.example.scms.dto.InstructorResponse;
import com.example.scms.entity.enums.UserRole;

import java.util.List;

public interface InstructorService {

    InstructorResponse createInstructor(InstructorRequest instructorRequest);
    List<InstructorResponse> getAllInstructors();
    InstructorResponse getInstructorById(Long id);
    InstructorResponse updateInstructor(Long id, InstructorRequest instructorRequest);
    void deleteInstructor(Long id);
}
