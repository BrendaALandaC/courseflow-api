package com.brenda.courseflow.course.controller;

import com.brenda.courseflow.course.dto.CourseCreateRequest;
import com.brenda.courseflow.course.dto.CourseResponse;
import com.brenda.courseflow.course.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name="bearerAuth")
@Tag(name = "Courses", description = "Controller for courses management, creating, querying, etc.")
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "Find all courses", description = "Find all courses",
            responses={ @ApiResponse(responseCode="200", description="OK")})
    @GetMapping
    public List<CourseResponse> findAll() {
        return courseService.findAll();
    }

    @Operation(summary = "Find by ID", description = "Find course by ID",
            responses={ @ApiResponse(responseCode="200", description="OK")})
    @GetMapping("/{id}")
    public CourseResponse findById(@PathVariable Long id) {
        return courseService.findById(id);
    }

    @Operation(summary = "Create course", description = "Create a new course",
            responses={ @ApiResponse(responseCode="201", description="Created")})
    @PostMapping
    public CourseResponse create(@RequestBody @Valid CourseCreateRequest request) {
        return courseService.create(request);
    }

    @Operation(summary = "Find active by ID", description = "Find courses by ID and status = Active",
            responses={ @ApiResponse(responseCode="200", description="OK")})
    @PatchMapping("/{id}/status")
    public CourseResponse changeStatus(
            @PathVariable Long id,
            @RequestParam Boolean active
    ) {
        return courseService.changeStatus(id, active);
    }
}