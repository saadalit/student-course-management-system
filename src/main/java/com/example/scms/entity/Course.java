package com.example.scms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "courses")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE courses SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Course Code is mandatory")
    @Column(name = "course_code", nullable = false, unique = true)
    private String courseCode;

    @NotBlank(message = "Course Name is mandatory")
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @NotNull(message = "Credits are mandatory")
    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 6, message = "Credits must be at most 6")
    @Column(name = "credit_hours", nullable = false)
    private Integer creditHours;

    @Column
    private String department;

    @Column
    private String semester;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
