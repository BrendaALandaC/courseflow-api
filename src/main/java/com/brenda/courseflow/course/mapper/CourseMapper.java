package com.brenda.courseflow.course.mapper;

import com.brenda.courseflow.course.dto.CourseCreateRequest;
import com.brenda.courseflow.course.dto.CourseResponse;
import com.brenda.courseflow.course.entity.Course;
import org.springframework.stereotype.Component;


    @Component
    public class CourseMapper {

        public Course toEntity(CourseCreateRequest request) {
            Course course = new Course();
            course.setTitle(request.getTitle());
            course.setDescription(request.getDescription());
            course.setModality(request.getModality());
            course.setLocation(request.getLocation());
            course.setStartDate(request.getStartDate());
            course.setEndDate(request.getEndDate());
            course.setMaxParticipants(request.getMaxParticipants());
            course.setInstructorName(request.getInstructorName());
            return course;
        }

        public CourseResponse toResponse(Course course) {
            return CourseResponse.builder()
                    .id(course.getId())
                    .title(course.getTitle())
                    .description(course.getDescription())
                    .modality(course.getModality())
                    .location(course.getLocation())
                    .startDate(course.getStartDate())
                    .endDate(course.getEndDate())
                    .maxParticipants(course.getMaxParticipants())
                    .active(course.getActive())
                    .instructorName(course.getInstructorName())
                    .build();
        }
}
