package com.brenda.courseflow.enrollment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentCreateRequest {

    @NotNull
    private Long courseId;

    @NotNull
    private Long participantId;
}