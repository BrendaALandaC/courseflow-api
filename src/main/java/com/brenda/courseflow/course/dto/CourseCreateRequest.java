package com.brenda.courseflow.course.dto;

import com.brenda.courseflow.course.enums.Modality;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CourseCreateRequest {

    @NotBlank
    private String title;

    private String description;

    private Modality modality;

    private String location;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @Min(1)
    private Integer maxParticipants;

    @NotBlank
    private String instructorName;
}