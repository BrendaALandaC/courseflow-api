package com.brenda.courseflow.enrollment.controller;

import com.brenda.courseflow.enrollment.dto.EnrollmentAttendanceRequest;
import com.brenda.courseflow.enrollment.dto.EnrollmentCreateRequest;
import com.brenda.courseflow.enrollment.dto.EnrollmentGradeRequest;
import com.brenda.courseflow.enrollment.dto.EnrollmentResponse;
import com.brenda.courseflow.enrollment.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name="bearerAuth")
@Tag(name = "Enrollments", description = "Controller for enrollment management")
@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Operation(summary = "Create enrollment", description = "Create a new enrollment",
            responses={ @ApiResponse(responseCode="201", description="Created")})
    @PostMapping
    public EnrollmentResponse create(
            @RequestBody @Valid EnrollmentCreateRequest request
    ) {
        return enrollmentService.create(request);
    }

    @Operation(summary = "Find by course ID", description = "Find by course ID",
            responses={ @ApiResponse(responseCode="200", description="OK")})
    @GetMapping("/course/{courseId}")
    public List<EnrollmentResponse> findByCourseId(
            @PathVariable Long courseId
    ) {
        return enrollmentService.findByCourseId(courseId);
    }

    @Operation(summary = "Find by participant ID", description = "Find by participant ID",
            responses={ @ApiResponse(responseCode="200", description="OK")})
    @GetMapping("/participant/{participantId}")
    public List<EnrollmentResponse> findByParticipantId(
            @PathVariable Long participantId
    ) {
        return enrollmentService.findByParticipantId(participantId);
    }


    @Operation(summary = "Find by enrollment ID", description = "Find by enrollment ID",
            responses={ @ApiResponse(responseCode="200", description="OK")})
    @GetMapping("/{id}")
    public EnrollmentResponse findById(
            @PathVariable Long id
    ) {

        return enrollmentService.findById(id);
    }

    @Operation(summary = "Attendance", description = "Update attendance")
    @PatchMapping("/{id}/attendance")
    public EnrollmentResponse updateAttendance(
            @PathVariable Long id,
            @RequestBody @Valid
            EnrollmentAttendanceRequest request
    ) {

        return enrollmentService.updateAttendance(
                id,
                request
        );
    }

    @Operation(summary = "Grade", description = "Update grade")
    @PatchMapping("/{id}/grade")
    public EnrollmentResponse updateGrade(
            @PathVariable Long id,
            @RequestBody @Valid
            EnrollmentGradeRequest request
    ) {

        return enrollmentService.updateGrade(
                id,
                request
        );
    }


    @Operation(
            summary = "Delete enrollment",
            description = "Delete enrollment"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id
    ) {

        enrollmentService.delete(id);
    }

}