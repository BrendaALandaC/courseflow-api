package com.brenda.courseflow.course.service;

import com.brenda.courseflow.course.dto.CourseCreateRequest;
import com.brenda.courseflow.course.dto.CourseResponse;
import com.brenda.courseflow.course.dto.CourseUpdateRequest;
import com.brenda.courseflow.course.entity.Course;
import com.brenda.courseflow.course.repository.CourseRepository;
import com.brenda.courseflow.shared.exception.BadRequestException;
import com.brenda.courseflow.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<CourseResponse> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CourseResponse findById(Long id) {
        Course course = getCourseById(id);
        return mapToResponse(course);
    }

    public CourseResponse create(CourseCreateRequest request) {
        validateDates(request.getStartDate(), request.getEndDate());

        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setModality(request.getModality());
        course.setLocation(request.getLocation());
        course.setStartDate(request.getStartDate());
        course.setEndDate(request.getEndDate());
        course.setMaxParticipants(request.getMaxParticipants());
        course.setInstructorName(request.getInstructorName());

        return mapToResponse(courseRepository.save(course));
    }

    public CourseResponse changeStatus(Long id, Boolean active) {
        Course course = getCourseById(id);
        course.setActive(active);
        return mapToResponse(courseRepository.save(course));
    }

    private Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new BadRequestException("End date cannot be before start date");
        }
    }

    public CourseResponse update(Long id, CourseUpdateRequest request) {
        validateDates(request.getStartDate(), request.getEndDate());

        Course course = getCourseById(id);

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setModality(request.getModality());
        course.setLocation(request.getLocation());
        course.setStartDate(request.getStartDate());
        course.setEndDate(request.getEndDate());
        course.setMaxParticipants(request.getMaxParticipants());
        course.setInstructorName(request.getInstructorName());

        return mapToResponse(courseRepository.save(course));
    }

    public void delete(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }

    private CourseResponse mapToResponse(Course course) {
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