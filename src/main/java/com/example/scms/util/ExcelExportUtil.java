package com.example.scms.util;

import com.example.scms.entity.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportUtil {

    public ByteArrayInputStream studentsToExcel(List<Student> students) throws IOException {
        String[] columns = {
                "ID", "Student Code", "First Name", "Last Name",
                "Gender", "Email", "Phone", "Date of Birth", "Status"
        };

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Students");

            // --- Header Style ---
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

            // Create Header Row
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(headerCellStyle);
            }

            // --- Data Rows ---
            int rowIdx = 1;
            for (Student student : students) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(student.getId() != null ? student.getId() : 0);
                row.createCell(1).setCellValue(student.getStudentCode() != null ? student.getStudentCode() : "");
                row.createCell(2).setCellValue(student.getFirstName() != null ? student.getFirstName() : "");
                row.createCell(3).setCellValue(student.getLastName() != null ? student.getLastName() : "");
                row.createCell(4).setCellValue(student.getGender() != null ? student.getGender() : "");
                row.createCell(5).setCellValue(student.getEmail() != null ? student.getEmail() : "");
                row.createCell(6).setCellValue(student.getPhone() != null ? student.getPhone() : "");
                row.createCell(7).setCellValue(
                        student.getDateOfBirth() != null ? student.getDateOfBirth().toString() : ""
                );
                row.createCell(8).setCellValue(student.getStatus() != null ? student.getStatus().toString() : "");
            }

            // Auto-fit column widths
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}