package com.brenda.courseflow.certificate.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CertificateResponse {

    private Long enrollmentId;
    private String certificateFolio;
    private Boolean certificateIssued;
}