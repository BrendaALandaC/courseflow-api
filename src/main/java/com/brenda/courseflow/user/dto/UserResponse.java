package com.brenda.courseflow.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private String username;
    private Boolean active;
    private String role;
}