package com.brenda.courseflow.certificate.controller;

import com.brenda.courseflow.certificate.dto.CertificateDetailResponse;
import com.brenda.courseflow.certificate.dto.CertificateResponse;
import com.brenda.courseflow.certificate.service.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


@SecurityRequirement(name="bearerAuth")
@Tag(name = "Certificates", description = "Certificate controller")

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @Operation(summary = "Certificate Issue", description = "Certificate issuing rules and folio generation")
    @PostMapping("/enrollment/{enrollmentId}")
    public CertificateResponse issueCertificate(
            @PathVariable Long enrollmentId
    ) {
        return certificateService.issueCertificate(enrollmentId);
    }


    @Operation(summary = "Certificate Detail", description = "Get certificate Detail")
    @GetMapping("/detail/enrollment/{enrollmentId}")
    public CertificateDetailResponse getCertificateDetail(
            @PathVariable Long enrollmentId
    ) {

        return certificateService.findCertificateDetail(
                enrollmentId
        );
    }

    @Operation(summary = "PDF", description = "Get certificate PDF")
    @GetMapping("/enrollment/{enrollmentId}/pdf")
    public ResponseEntity<byte[]> generatePdf(
            @PathVariable Long enrollmentId
    ) {

        byte[] pdf =
                certificateService.generatePdf(
                        enrollmentId
                );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=certificate.pdf"
                )
                .contentType(
                        MediaType.APPLICATION_PDF
                )
                .body(pdf);
    }
}