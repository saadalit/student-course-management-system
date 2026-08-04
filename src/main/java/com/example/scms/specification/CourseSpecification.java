package com.example.scms.specification;

import com.example.scms.entity.Course;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CourseSpecification {

    public static Specification<Course> filterCourses(
            Long id,
            String courseName,
            String courseCode,
            String department,
            String semester,
            Integer creditHours
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. ID Filter (Exact Match)
            if (id != null) {
                predicates.add(cb.equal(root.get("id"), id));
            }

            // 2. Course Name Filter (Partial Match)
            if (StringUtils.hasText(courseName)) {
                predicates.add(cb.like(cb.lower(root.get("courseName")), "%" + courseName.toLowerCase().trim() + "%"));
            }

            // 3. Course Code Filter (Exact/Case-insensitive Match)
            if (StringUtils.hasText(courseCode)) {
                predicates.add(cb.equal(cb.lower(root.get("courseCode")), courseCode.toLowerCase().trim()));
            }

            // 4. Department Filter (Partial Match)
            if (StringUtils.hasText(department)) {
                predicates.add(cb.like(cb.lower(root.get("department")), "%" + department.toLowerCase().trim() + "%"));
            }

            // 5. Semester Filter (Exact/Case-insensitive Match)
            if (StringUtils.hasText(semester)) {
                predicates.add(cb.equal(cb.lower(root.get("semester")), semester.toLowerCase().trim()));
            }

            // 6. Credit Hours Filter (Exact Match)
            if (creditHours != null) {
                predicates.add(cb.equal(root.get("creditHours"), creditHours));
            }

            // Combines ONLY the provided criteria using strict AND logic
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}