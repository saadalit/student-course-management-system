package com.example.scms.specification;

import com.example.scms.entity.Instructor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class InstructorSpecification {

    public static Specification<Instructor> filterInstructors(
            Long id,
            String name,
            String instructorCode,
            String email,
            String department,
            String designation
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Instructor ID Filter (Exact Match)
            if (id != null) {
                predicates.add(cb.equal(root.get("id"), id));
            }

            // 2. Name Filter (Partial Match)
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase().trim() + "%"));
            }

            // 3. Instructor Code Filter (Exact/Case-insensitive Match)
            if (StringUtils.hasText(instructorCode)) {
                predicates.add(cb.equal(cb.lower(root.get("instructorCode")), instructorCode.toLowerCase().trim()));
            }

            // 4. Email Filter (Partial Match)
            if (StringUtils.hasText(email)) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase().trim() + "%"));
            }

            // 5. Department Filter (Partial Match)
            if (StringUtils.hasText(department)) {
                predicates.add(cb.like(cb.lower(root.get("department")), "%" + department.toLowerCase().trim() + "%"));
            }

            // 6. Designation Filter (Partial Match)
            if (StringUtils.hasText(designation)) {
                predicates.add(cb.like(cb.lower(root.get("designation")), "%" + designation.toLowerCase().trim() + "%"));
            }

            // Combines all active individual parameters using strict AND logic
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}