package com.example.scms.service.impl;

import com.example.scms.dto.InstructorRequest;
import com.example.scms.dto.report.DepartmentCountResponse;
import com.example.scms.dto.report.SystemSummaryReport;
import com.example.scms.repository.CourseRepository;
import com.example.scms.repository.InstructorRepository;
import com.example.scms.repository.StudentRepository;
import com.example.scms.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DialectOverride;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    @Override
    @Transactional(readOnly = true)
    public SystemSummaryReport getSystemSummary()
    {
        log.info("Generating system summary report");
        long totalStudents = studentRepository.count();
        long totalCourses = courseRepository.count();
        long totalInstructors = instructorRepository.count();
        long totalActiveStudents = studentRepository.countActiveStudents();

        SystemSummaryReport report = new SystemSummaryReport();
        report.setTotalStudents(totalStudents);
        report.setTotalInstructors(totalInstructors);
        report.setTotalCourses(totalCourses);
        report.setActiveStudents(totalActiveStudents);

        log.info("System summary report generated: Total Students - {}, Total Courses - {}", totalStudents, totalCourses);
        return report;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentCountResponse> getStudentCountByDepartment()
    {
        log.info("Generating students count by department report");
        List<DepartmentCountResponse> departmentCounts = studentRepository.countStudentsByDepartment();
        log.info("Students count by department report generated with {} departments", departmentCounts.size());
        return departmentCounts;
    }


}
