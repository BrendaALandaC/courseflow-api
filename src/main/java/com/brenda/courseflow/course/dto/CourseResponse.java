package com.brenda.courseflow.course.dto;

import com.brenda.courseflow.course.enums.Modality;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CourseResponse {

    private Long id;
    private String title;
    private String description;
    private Modality modality;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxParticipants;
    private Boolean active;
    private String instructorName;
}