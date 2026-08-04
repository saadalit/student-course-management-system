package com.example.scms.repository;

import com.example.scms.dto.report.DepartmentCountResponse;
import com.example.scms.entity.Student;
import com.example.scms.entity.enums.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    Optional<Student> findByStudentCode(String studentCode);
    Optional<Student> findByEmail(String email);
    Boolean existsByStudentCode(String studentCode);
    Boolean existsByEmail(String email);
    List<Student> findByFirstNameIgnoreCaseOrLastNameIgnoreCase(String firstName, String lastName);
    List<Student> findByStatus(StudentStatus status);

    @Query("SELECT COUNT(DISTINCT s.id) FROM Student s WHERE s.status = 'ACTIVE'")
    Long countActiveStudents();

    @Query("SELECT new com.example.scms.dto.report.DepartmentCountResponse(e.course.department, COUNT(DISTINCT e.student.id)) " +
            "FROM Enrollment e GROUP BY e.course.department")
    List<DepartmentCountResponse> countStudentsByDepartment();
}
