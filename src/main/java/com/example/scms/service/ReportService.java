package com.example.scms.service;

import com.example.scms.dto.report.DepartmentCountResponse;
import com.example.scms.dto.report.SystemSummaryReport;

import java.util.List;

public interface ReportService {

    SystemSummaryReport getSystemSummary();
    List<DepartmentCountResponse> getStudentCountByDepartment();
}
