package com.brenda.courseflow.user.controller;

import com.brenda.courseflow.user.dto.UserCreateRequest;
import com.brenda.courseflow.user.dto.UserResponse;
import com.brenda.courseflow.user.dto.UserUpdateRequest;
import com.brenda.courseflow.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "UserController", description = "Controller for user management, creating, querying, etc.")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Find all users", description = "Find all users",
            responses={ @ApiResponse(responseCode="200", description="OK")})
    @GetMapping
    public List<UserResponse> findAll() {

        return userService.findAll();
    }

    @Operation(summary = "Find by ID", description = "Find user by ID",
            responses={ @ApiResponse(responseCode="200", description="OK")})
    @GetMapping("/{id}")
    public UserResponse findById(
            @PathVariable Long id
    ) {

        return userService.findById(id);
    }

    @Operation(summary = "Create user", description = "Create a new user",
            responses={ @ApiResponse(responseCode="201", description="Created")})
    @PostMapping
    public UserResponse create(
            @RequestBody @Valid UserCreateRequest request
    ) {

        return userService.create(request);
    }


    @Operation(summary = "Update user", description = "Update user",
            responses={ @ApiResponse(responseCode="200", description="OK")})
    @PutMapping("/{id}")
    public UserResponse update(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdateRequest request
    ) {
        return userService.update(id, request);
    }


    @Operation(summary = "Delete user", description = "Delete user")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}