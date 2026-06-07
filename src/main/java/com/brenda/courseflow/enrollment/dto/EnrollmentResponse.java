package com.brenda.courseflow.enrollment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnrollmentResponse {

    private Long id;

    private Long courseId;
    private String courseTitle;

    private Long participantId;
    private String participantName;

    private Boolean attendanceConfirmed;

    private Double grade;

    private Boolean approved;

    private Boolean certificateIssued;
}