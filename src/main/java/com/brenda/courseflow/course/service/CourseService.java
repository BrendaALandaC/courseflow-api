package com.brenda.courseflow.course.service;

import com.brenda.courseflow.course.dto.CourseCreateRequest;
import com.brenda.courseflow.course.dto.CourseResponse;
import com.brenda.courseflow.course.dto.CourseUpdateRequest;
import com.brenda.courseflow.course.entity.Course;
import com.brenda.courseflow.course.mapper.CourseMapper;
import com.brenda.courseflow.course.repository.CourseRepository;
import com.brenda.courseflow.shared.exception.BadRequestException;
import com.brenda.courseflow.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.brenda.courseflow.shared.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;


    public PageResponse<CourseResponse> findAllPageable(Boolean active, Pageable pageable) {

        Page<Course> coursePage = active == null
                ? courseRepository.findAll(pageable)
                : courseRepository.findByActive(active, pageable);

        Page<CourseResponse> responsePage =
                coursePage.map(courseMapper::toResponse);

        return PageResponse.<CourseResponse>builder()
                .content(responsePage.getContent())
                .page(responsePage.getNumber())
                .size(responsePage.getSize())
                .totalElements(responsePage.getTotalElements())
                .totalPages(responsePage.getTotalPages())
                .last(responsePage.isLast())
                .build();
    }


    public CourseResponse findById(Long id) {
        Course course = getCourseById(id);
        return courseMapper.toResponse(course);
    }

    public CourseResponse create(CourseCreateRequest request) {
        validateDates(request.getStartDate(), request.getEndDate());
        Course course = courseMapper.toEntity(request);
        return courseMapper.toResponse(courseRepository.save(course));
    }

    public CourseResponse changeStatus(Long id, Boolean active) {
        Course course = getCourseById(id);
        course.setActive(active);
        return courseMapper.toResponse(courseRepository.save(course));
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
        return courseMapper.toResponse(courseRepository.save(course));
    }

    public void delete(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }


}