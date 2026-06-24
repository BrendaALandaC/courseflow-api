package com.brenda.courseflow.course.controller;

import com.brenda.courseflow.course.dto.CourseCreateRequest;
import com.brenda.courseflow.course.dto.CourseResponse;
import com.brenda.courseflow.course.dto.CourseUpdateRequest;
import com.brenda.courseflow.course.service.CourseService;
import com.brenda.courseflow.shared.response.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name="bearerAuth")
@Tag(name = "Courses", description = "Controller for courses management, creating, querying, etc.")
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;



    @Operation(summary = "Find all courses with pagination", description = "Find all courses: pagination and active filters")
    @GetMapping
    public PageResponse<CourseResponse> findAllPageable(
            @RequestParam(required = false) Boolean active,
            @PageableDefault(
                    size = 10,
                    sort = "startDate",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return courseService.findAllPageable(active, pageable);
    }
    @Operation(summary = "Find by ID", description = "Find course by ID")
    @GetMapping("/{id}")
    public CourseResponse findById(@PathVariable Long id) {
        return courseService.findById(id);
    }

    @Operation(summary = "Create course", description = "Create a new course",
            responses={ @ApiResponse(responseCode="201", description="Created")})
    @PostMapping
    public ResponseEntity<CourseResponse> create(@RequestBody @Valid CourseCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.create(request));
    }

    @Operation(summary = "Change status", description = "Change status of courses by ID and status active = true/false")
    @PatchMapping("/{id}/status")
    public CourseResponse changeStatus(
            @PathVariable Long id,
            @RequestParam Boolean active
    ) {
        return courseService.changeStatus(id, active);
    }

    @Operation(summary = "Update course", description = "Update course",
            responses={ @ApiResponse(responseCode="200", description="OK")})
    @PutMapping("/{id}")
    public CourseResponse update(
            @PathVariable Long id,
            @RequestBody @Valid CourseUpdateRequest request
    ) {
        return courseService.update(id, request);
    }

     @Operation(
            summary = "Delete course",
            description = "Delete course"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Not found", content = @Content)
    })
     @DeleteMapping("/{id}")
     public ResponseEntity<Void> delete(@PathVariable Long id) {
         courseService.delete(id);
         return ResponseEntity.noContent().build();
     }
}