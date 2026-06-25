package com.brenda.courseflow.participant.mapper;

import com.brenda.courseflow.participant.dto.ParticipantCreateRequest;
import com.brenda.courseflow.participant.dto.ParticipantResponse;
import com.brenda.courseflow.participant.entity.Participant;
import org.springframework.stereotype.Component;


@Component
public class ParticipantMapper {

    public Participant toEntity(ParticipantCreateRequest request) {
        Participant participant = new Participant();
        participant.setFirstName(request.getFirstName());
        participant.setLastName(request.getLastName());
        participant.setEmail(request.getEmail());
        participant.setPhone(request.getPhone());
        participant.setInstitution(request.getInstitution());
        return participant;
    }

    public ParticipantResponse toResponse(Participant participant) {
        return ParticipantResponse.builder()
                .id(participant.getId())
                .firstName(participant.getFirstName())
                .lastName(participant.getLastName())
                .email(participant.getEmail())
                .phone(participant.getPhone())
                .institution(participant.getInstitution())
                .build();
    }
}
