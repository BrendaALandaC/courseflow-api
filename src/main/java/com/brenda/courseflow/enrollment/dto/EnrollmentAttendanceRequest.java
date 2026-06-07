package com.brenda.courseflow.enrollment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentAttendanceRequest {

    @NotNull
    private Boolean attendanceConfirmed;
}