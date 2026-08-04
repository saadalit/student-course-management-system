package com.example.scms.specification;

import com.example.scms.entity.Student;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {

    public static Specification<Student> filterStudents(
            Long id,
            String studentCode,
            String firstName,
            String lastName,
            String email,
            String phone,
            String gender,
            String status
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Student ID Filter (Exact Match)
            if (id != null) {
                predicates.add(cb.equal(root.get("id"), id));
            }

            // 2. Student Code Filter (Exact/Case-insensitive Match)
            if (StringUtils.hasText(studentCode)) {
                predicates.add(cb.equal(cb.lower(root.get("studentCode")), studentCode.toLowerCase().trim()));
            }

            // 3. First Name Filter (Partial Match)
            if (StringUtils.hasText(firstName)) {
                predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase().trim() + "%"));
            }

            // 4. Last Name Filter (Partial Match)
            if (StringUtils.hasText(lastName)) {
                predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase().trim() + "%"));
            }

            // 5. Email Filter (Partial Match)
            if (StringUtils.hasText(email)) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase().trim() + "%"));
            }

            // 6. Phone Filter (Partial Match)
            if (StringUtils.hasText(phone)) {
                predicates.add(cb.like(root.get("phone"), "%" + phone.trim() + "%"));
            }

            // 7. Gender Filter (Exact Match)
            if (StringUtils.hasText(gender)) {
                predicates.add(cb.equal(cb.upper(root.get("gender")), gender.trim().toUpperCase()));
            }

            // 8. Status Filter (Exact Match)
            if (StringUtils.hasText(status)) {
                predicates.add(cb.equal(cb.upper(root.get("status")), status.trim().toUpperCase()));
            }

            // Combines all active individual criteria using strict AND logic
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}