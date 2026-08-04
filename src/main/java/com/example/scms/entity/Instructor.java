package com.example.scms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "instructors")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE instructors SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Instructor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Instructor Code is mandatory")
    @Column(name = "instructor_code", nullable = false, unique = true)
    private String instructorCode;

    @NotBlank(message = "Name is mandatory")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String phone;

    @Column
    private String department;

    @Column
    private String designation;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
