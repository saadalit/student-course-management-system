package com.example.scms.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {

    @NotBlank(message = "Course Code is mandatory")
    private String courseCode;

    @NotBlank(message = "Course Name is mandatory")
    private String courseName;

    @NotNull(message = "Credits are mandatory")
    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 6, message = "Credits must be at most 6")
    private Integer creditHours;

    @NotBlank(message = "Department is mandatory")
    private String department;

    private String semester;
}
