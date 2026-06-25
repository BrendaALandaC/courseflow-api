package com.brenda.courseflow.participant.controller;

import com.brenda.courseflow.participant.dto.ParticipantCreateRequest;
import com.brenda.courseflow.participant.dto.ParticipantResponse;
import com.brenda.courseflow.participant.dto.ParticipantUpdateRequest;
import com.brenda.courseflow.participant.service.ParticipantService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import com.brenda.courseflow.shared.response.PageResponse;
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
@Tag(name = "Participants", description = "Controller for participants management, creating, querying, etc.")
@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @Operation(summary = "Find all participants with filters and pagination", description = "Find all participants: filtering by name amd pagination")
    @GetMapping
    public PageResponse<ParticipantResponse> findAll(

            @RequestParam(required = false)
            String name,

            @PageableDefault(
                    size = 10,
                    sort = "lastName",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ) {

        return participantService.findAll(
                name,
                pageable
        );
    }

    @Operation(summary = "Find by ID", description = "Find participant by ID",
              responses={ @ApiResponse(responseCode="200", description="OK")})
    @GetMapping("/{id}")
    public ParticipantResponse findById(@PathVariable Long id) {
        return participantService.findById(id);
    }

    @Operation(summary = "Create participant", description = "Create a new participant",
            responses={ @ApiResponse(responseCode="201", description="Created")})
    @PostMapping
    public ParticipantResponse create(@RequestBody @Valid ParticipantCreateRequest request) {
        return participantService.create(request);
    }

    @Operation(summary = "Update participant", description = "Update participant",
            responses={ @ApiResponse(responseCode="200", description="OK")})
    @PutMapping("/{id}")
    public ParticipantResponse update(
            @PathVariable Long id,
            @RequestBody @Valid ParticipantUpdateRequest request
    ) {
        return participantService.update(id, request);
    }


    @Operation(
            summary = "Delete participant",
            description = "Delete participant"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        participantService.delete(id);
    }
}