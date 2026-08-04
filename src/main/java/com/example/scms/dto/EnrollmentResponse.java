package com.example.scms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResponse {

    private Long id;
    private Long studentId;
    private String studentName; // Optional: Helpful to include for UI displays
    private Long courseId;
    private String courseName;  // Optional: Helpful to include for UI displays
    private LocalDate enrollmentDate;
}