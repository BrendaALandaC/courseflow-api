package com.brenda.courseflow.certificate.controller;

import com.brenda.courseflow.certificate.dto.CertificateResponse;
import com.brenda.courseflow.certificate.service.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@SecurityRequirement(name="bearerAuth")
@Tag(name = "Certificates", description = "Certificate controller")

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @Operation(summary = "Issue certificate", description = "Certificate issuing rules and folio generation")
    @PostMapping("/enrollment/{enrollmentId}")
    public CertificateResponse issueCertificate(
            @PathVariable Long enrollmentId
    ) {
        return certificateService.issueCertificate(enrollmentId);
    }
}