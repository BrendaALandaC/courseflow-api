package com.brenda.courseflow.participant.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipantResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String institution;
}