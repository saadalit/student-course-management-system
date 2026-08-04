package com.example.scms.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemSummaryReport {

    private Long totalStudents;
    private Long totalInstructors;
    private Long totalCourses;
    private Long activeStudents;
}
