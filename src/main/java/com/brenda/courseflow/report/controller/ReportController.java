package com.brenda.courseflow.report.controller;

import com.brenda.courseflow.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@SecurityRequirement(name="bearerAuth")
@Tag(name = "Excel Reports", description = "Excel reports")

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Generate report ", description = "Generate a participants report in Excel")
    @GetMapping(
            "/courses/{courseId}/participants/excel"
    )
    public ResponseEntity<byte[]> generateParticipantsExcel(
            @PathVariable Long courseId
    ) {

        byte[] excel =
                reportService.generateParticipantsExcel(
                        courseId
                );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=participants-course-"
                                + courseId
                                + ".xlsx"
                )
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )
                .body(excel);
    }
}