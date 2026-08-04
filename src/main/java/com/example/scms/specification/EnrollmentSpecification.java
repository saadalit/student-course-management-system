package com.example.scms.specification;

import com.example.scms.entity.Enrollment;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentSpecification {

    public static Specification<Enrollment> filterEnrollments(
            Long id,
            Long studentId,
            Long courseId,
            LocalDate enrollmentDate
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Enrollment ID (Exact Match)
            if (id != null) {
                predicates.add(cb.equal(root.get("id"), id));
            }

            // 2. Student ID Filter (Exact Foreign Key Match)
            if (studentId != null) {
                predicates.add(cb.equal(root.get("student").get("id"), studentId));
            }

            // 3. Course ID Filter (Exact Foreign Key Match)
            if (courseId != null) {
                predicates.add(cb.equal(root.get("course").get("id"), courseId));
            }

            // 4. Enrollment Date Filter (Exact Date Match)
            if (enrollmentDate != null) {
                predicates.add(cb.equal(root.get("enrollmentDate"), enrollmentDate));
            }

            // Combines all present filters using strict AND logic
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}