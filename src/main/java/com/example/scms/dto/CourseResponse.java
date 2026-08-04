package com.example.scms.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponse {

    private Long id;
    private String courseCode;
    private String courseName;
    private Integer creditHours;
    private String department;
    private String semester;
}
