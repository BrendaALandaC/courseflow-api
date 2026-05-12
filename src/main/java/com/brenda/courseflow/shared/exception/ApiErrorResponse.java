package com.brenda.courseflow.shared.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;
}