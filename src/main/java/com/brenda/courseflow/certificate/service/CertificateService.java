package com.brenda.courseflow.certificate.service;

import com.brenda.courseflow.certificate.dto.CertificateResponse;
import com.brenda.courseflow.enrollment.entity.Enrollment;
import com.brenda.courseflow.enrollment.repository.EnrollmentRepository;
import com.brenda.courseflow.shared.exception.BadRequestException;
import com.brenda.courseflow.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final EnrollmentRepository enrollmentRepository;

    public CertificateResponse issueCertificate(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));

        validateCertificateRules(enrollment);

        if (Boolean.TRUE.equals(enrollment.getCertificateIssued())) {
            throw new BadRequestException("Certificate has already been issued");
        }

        String folio = generateFolio(enrollment);

        enrollment.setCertificateFolio(folio);
        enrollment.setCertificateIssued(true);

        enrollmentRepository.save(enrollment);

        return CertificateResponse.builder()
                .enrollmentId(enrollment.getId())
                .certificateFolio(enrollment.getCertificateFolio())
                .certificateIssued(enrollment.getCertificateIssued())
                .build();
    }

    private void validateCertificateRules(Enrollment enrollment) {
        if (!Boolean.TRUE.equals(enrollment.getAttendanceConfirmed())) {
            throw new BadRequestException("Participant attendance must be confirmed before issuing a certificate");
        }

        if (!Boolean.TRUE.equals(enrollment.getApproved())) {
            throw new BadRequestException("Participant must pass the course before receiving a certificate");
        }

        if (enrollment.getGrade() == null || enrollment.getGrade() < 7) {
            throw new BadRequestException("Participant grade must be at least 7 to receive a certificate");
        }
    }

    private String generateFolio(Enrollment enrollment) {
        return String.format(
                "CERT-%s-%06d",
                Year.now().getValue(),
                enrollment.getId()
        );
    }
}