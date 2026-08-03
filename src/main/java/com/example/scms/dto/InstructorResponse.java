package com.example.scms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorResponse {

    private Long id;
    private String instructorCode;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String designation;

}
