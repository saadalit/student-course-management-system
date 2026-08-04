package com.example.scms.service;

import com.example.scms.dto.InstructorRequest;
import com.example.scms.dto.InstructorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InstructorService {

    InstructorResponse createInstructor(InstructorRequest instructorRequest);

    // Updated to handle Search, Filter, Pagination & Sorting in one clean service contract
    Page<InstructorResponse> getAllInstructors(
            Long id,
            String name,
            String instructorCode,
            String email,
            String department,
            String designation,
            Pageable pageable
    );
    InstructorResponse getInstructorById(Long id);

    InstructorResponse updateInstructor(Long id, InstructorRequest instructorRequest);

    void deleteInstructor(Long id);
}