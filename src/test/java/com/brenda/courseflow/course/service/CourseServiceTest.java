package com.brenda.courseflow.course.service;

import com.brenda.courseflow.course.dto.CourseCreateRequest;
import com.brenda.courseflow.course.dto.CourseResponse;
import com.brenda.courseflow.course.entity.Course;
import com.brenda.courseflow.course.mapper.CourseMapper;
import com.brenda.courseflow.course.repository.CourseRepository;
import com.brenda.courseflow.shared.exception.BadRequestException;
import com.brenda.courseflow.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    // ── helpers ──────────────────────────────────────────────

    private Course buildCourse(Long id) {
        Course c = new Course();
        c.setId(id);
        c.setTitle("Spring Boot Basics");
        c.setStartDate(LocalDate.of(2025, 8, 1));
        c.setEndDate(LocalDate.of(2025, 8, 15));
        c.setActive(true);
        return c;
    }

    private CourseResponse buildResponse(Long id) {
        return CourseResponse.builder()
                .id(id)
                .title("Spring Boot Basics")
                .active(true)
                .build();
    }

    // ── tests ─────────────────────────────────────────────────

    @Test
    void findAll_shouldReturnMappedList() {
        Course course = buildCourse(1L);
        when(courseRepository.findAll()).thenReturn(List.of(course));
        when(courseMapper.toResponse(course)).thenReturn(buildResponse(1L));

        List<CourseResponse> result = courseService.findAll();

        assertEquals(1, result.size());
        assertEquals("Spring Boot Basics", result.get(0).getTitle());
    }

    @Test
    void findById_shouldReturnResponse_whenCourseExists() {
        Course course = buildCourse(1L);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseMapper.toResponse(course)).thenReturn(buildResponse(1L));

        CourseResponse result = courseService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void findById_shouldThrow_whenCourseNotFound() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> courseService.findById(99L));
    }

    @Test
    void create_shouldThrow_whenEndDateBeforeStartDate() {
        CourseCreateRequest request = new CourseCreateRequest();
        request.setStartDate(LocalDate.of(2025, 9, 1));
        request.setEndDate(LocalDate.of(2025, 8, 1));

        assertThrows(BadRequestException.class,
                () -> courseService.create(request));

        verify(courseRepository, never()).save(any());
    }

    @Test
    void changeStatus_shouldUpdateAndReturn() {
        Course course = buildCourse(1L);
        CourseResponse expected = buildResponse(1L);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toResponse(course)).thenReturn(expected);

        CourseResponse result = courseService.changeStatus(1L, false);

        assertFalse(course.getActive());
        assertNotNull(result);
    }
}