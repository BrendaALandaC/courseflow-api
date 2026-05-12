package com.brenda.courseflow.user.controller;

import com.brenda.courseflow.user.dto.UserCreateRequest;
import com.brenda.courseflow.user.dto.UserResponse;
import com.brenda.courseflow.user.service.UserService;
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

    @GetMapping
    public List<UserResponse> findAll() {

        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponse findById(
            @PathVariable Long id
    ) {

        return userService.findById(id);
    }

    @PostMapping
    public UserResponse create(
            @RequestBody @Valid UserCreateRequest request
    ) {

        return userService.create(request);
    }
}