package com.example.scms.controller;


import com.example.scms.dto.report.DepartmentCountResponse;
import com.example.scms.dto.report.SystemSummaryReport;
import com.example.scms.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/summary")
    public ResponseEntity<SystemSummaryReport> getSystemSummary()
    {
        SystemSummaryReport report = reportService.getSystemSummary();
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentCountResponse>> getStudentsByDepartment()
    {
        List<DepartmentCountResponse> report = reportService.getStudentCountByDepartment();
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
