package com.brenda.courseflow.course.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CourseResponse {

    private Long id;
    private String title;
    private String description;
    private String modality;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxParticipants;
    private Boolean active;
    private String instructorName;
}