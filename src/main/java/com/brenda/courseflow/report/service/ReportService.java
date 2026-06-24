package com.brenda.courseflow.report.service;


import com.brenda.courseflow.course.repository.CourseRepository;
import com.brenda.courseflow.enrollment.entity.Enrollment;
import com.brenda.courseflow.enrollment.repository.EnrollmentRepository;
import com.brenda.courseflow.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    public byte[] generateParticipantsExcel(Long courseId) {

        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found");
        }

        List<Enrollment> enrollments =
                enrollmentRepository.findByCourseId(courseId);

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream =
                        new ByteArrayOutputStream()
        ) {

            Sheet sheet =
                    workbook.createSheet("Participants");

            createHeader(sheet);

            int rowIndex = 1;

            for (Enrollment enrollment : enrollments) {

                Row row = sheet.createRow(rowIndex++);
                int col = 0;

                row.createCell(col++)
                        .setCellValue(
                                enrollment.getParticipant().getId()
                        );

                row.createCell(col++)
                        .setCellValue(
                                enrollment.getParticipant().getFirstName()
                                        + " "
                                        + enrollment.getParticipant().getLastName()
                        );

                row.createCell(col++)
                        .setCellValue(
                                enrollment.getParticipant().getEmail()
                        );

                row.createCell(col++)
                        .setCellValue(
                                enrollment.getParticipant().getInstitution()
                        );

                row.createCell(col++)
                        .setCellValue(
                                Boolean.TRUE.equals(
                                        enrollment.getAttendanceConfirmed()
                                )
                                        ? "YES"
                                        : "NO"
                        );

                row.createCell(col++)
                        .setCellValue(
                                enrollment.getGrade() != null
                                        ? enrollment.getGrade()
                                        : 0
                        );

                row.createCell(col++)
                        .setCellValue(
                                Boolean.TRUE.equals(
                                        enrollment.getApproved()
                                )
                                        ? "YES"
                                        : "NO"
                        );

                row.createCell(col++)
                        .setCellValue(
                                Boolean.TRUE.equals(
                                        enrollment.getCertificateIssued()
                                )
                                        ? "YES"
                                        : "NO"
                        );

                row.createCell(col++)
                        .setCellValue(
                                enrollment.getCertificateFolio() != null
                                        ? enrollment.getCertificateFolio()
                                        : ""
                        );
            }

            for (int i = 0; i < 9; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);

            return outputStream.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Error generating Excel report",
                    ex
            );
        }
    }

    private void createHeader(Sheet sheet) {

        List<String> headers = new ArrayList<>(Arrays.asList("Participant ID",
                "Participant Name", "Email", "Institution", "Attendance",
                "Grade", "Approved", "Certificate Issued", "Certificate Folio"
        ));

        Row header = sheet.createRow(0);

        for (int i = 1; i <= headers.size(); i++) {
            header.createCell(i-1).setCellValue(headers.get(i - 1));
        }


    }
}