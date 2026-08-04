package com.example.scms.entity;

import com.example.scms.entity.enums.StudentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE students SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Student extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Student Code is mandatory")
    @Column(name = "student_code", nullable = false, unique = true)
    private String studentCode;

    @NotBlank(message = "First Name is mandatory")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last Name is mandatory")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^\\d{10,15}$", message = "Phone must contain 10–15 digits")
    private String phone;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column
    private String gender;

    @NotNull(message = "Status is mandatory")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StudentStatus status;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

}
