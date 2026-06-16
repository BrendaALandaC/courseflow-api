package com.brenda.courseflow.certificate.service;

import com.brenda.courseflow.certificate.dto.CertificateDetailResponse;
import com.brenda.courseflow.certificate.dto.CertificateResponse;
import com.brenda.courseflow.enrollment.entity.Enrollment;
import com.brenda.courseflow.enrollment.repository.EnrollmentRepository;
import com.brenda.courseflow.shared.exception.BadRequestException;
import com.brenda.courseflow.shared.exception.ResourceNotFoundException;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfContentByte;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

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


    public CertificateDetailResponse findCertificateDetail(
            Long enrollmentId
    ) {

        Enrollment enrollment = enrollmentRepository.findById(
                        enrollmentId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment not found"
                        ));

        return CertificateDetailResponse.builder()
                .enrollmentId(enrollment.getId())
                .participantName(
                        enrollment.getParticipant().getFirstName()
                                + " "
                                + enrollment.getParticipant().getLastName()
                )
                .courseTitle(
                        enrollment.getCourse().getTitle()
                )
                .instructorName(
                        enrollment.getCourse().getInstructorName()
                )
                .grade(
                        enrollment.getGrade()
                )
                .approved(
                        enrollment.getApproved()
                )
                .certificateFolio(
                        enrollment.getCertificateFolio()
                )
                .certificateIssued(
                        enrollment.getCertificateIssued()
                )
                .build();
    }


    public byte[] generatePdf(Long enrollmentId) {

        CertificateDetailResponse detail =
                findCertificateDetail(enrollmentId);

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

           Document document =
                    new Document(
                            PageSize.A4.rotate()
                    );


            PdfWriter writer = PdfWriter.getInstance(
                    document,
                    outputStream
            );


            document.open();

            PdfContentByte canvas = writer.getDirectContent();

            canvas.rectangle(
                    36,
                    36,
                    770,
                    523
            );

            canvas.stroke();

            Font titleFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            22
                    );

            Font nameFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            20
                    );

            Font instructorFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            16
                    );

            Font textFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA,
                            14
                    );


            Paragraph title =
                    new Paragraph(
                            "CERTIFICATE OF COMPLETION",
                            titleFont
                    );

            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(30f);

            document.add(title);

            Paragraph thisCertificate = new Paragraph("This certifies that", textFont);
            thisCertificate.setAlignment(Element.ALIGN_CENTER);
            thisCertificate.setSpacingAfter(25f);
            document.add(thisCertificate);

            Paragraph participantName = new Paragraph(detail.getParticipantName(), nameFont);
            participantName.setAlignment(Element.ALIGN_CENTER);
            participantName.setSpacingAfter(25f);
            document.add(participantName);

            Paragraph succesfullyCompleted = new Paragraph("has successfully completed", textFont);
            succesfullyCompleted.setAlignment(Element.ALIGN_CENTER);
            succesfullyCompleted.setSpacingAfter(15f);
            document.add(succesfullyCompleted);

            Paragraph courseTitle = new Paragraph(detail.getCourseTitle(), nameFont);
            courseTitle.setAlignment(Element.ALIGN_CENTER);
            courseTitle.setSpacingAfter(15f);
            document.add(courseTitle);

           Paragraph instructor = new Paragraph("Instructor: ", textFont);
           instructor.setAlignment(Element.ALIGN_CENTER);
           instructor.setSpacingAfter(15f);
           document.add(instructor);

            Paragraph instructorName = new Paragraph(detail.getInstructorName(), instructorFont);
            instructorName.setAlignment(Element.ALIGN_CENTER);
            instructorName.setSpacingAfter(15f);
            document.add(instructorName);

            Paragraph certificateText = new Paragraph("Certificate Folio: ", textFont);
            certificateText.setAlignment(Element.ALIGN_CENTER);
            certificateText.setSpacingAfter(15f);
            document.add(certificateText);

            Paragraph certificateFolio = new Paragraph(detail.getCertificateFolio(), instructorFont);
            certificateFolio.setAlignment(Element.ALIGN_CENTER);
            certificateFolio.setSpacingAfter(15f);
            document.add(certificateFolio);

            Paragraph issueDate = new Paragraph("Issued on June 13, 2026", textFont);
            issueDate.setAlignment(Element.ALIGN_CENTER);
            issueDate.setSpacingAfter(15f);
            document.add(issueDate);

            Paragraph signatureLine = new Paragraph("____________________", textFont);
            signatureLine.setAlignment(Element.ALIGN_CENTER);
            document.add(signatureLine);

            Paragraph signatureText = new Paragraph("Instructor Signature", textFont);
            signatureText.setAlignment(Element.ALIGN_CENTER);
            signatureText.setSpacingAfter(5f);
            document.add(signatureText);

            Paragraph generated = new Paragraph("Generated by CourseFlow API", textFont);
            generated.setAlignment(Element.ALIGN_RIGHT);
            document.add(generated);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Error generating PDF",
                    ex
            );
        }
    }
}