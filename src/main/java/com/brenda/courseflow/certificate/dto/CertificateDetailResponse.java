package com.brenda.courseflow.certificate.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CertificateDetailResponse {

    private Long enrollmentId;

    private String participantName;

    private String courseTitle;

    private String instructorName;

    private Double grade;

    private Boolean approved;

    private String certificateFolio;

    private Boolean certificateIssued;
}