package com.brenda.courseflow.participant.service;

import com.brenda.courseflow.participant.dto.ParticipantCreateRequest;
import com.brenda.courseflow.participant.dto.ParticipantResponse;
import com.brenda.courseflow.participant.dto.ParticipantUpdateRequest;
import com.brenda.courseflow.participant.entity.Participant;
import com.brenda.courseflow.participant.repository.ParticipantRepository;
import com.brenda.courseflow.shared.exception.BadRequestException;
import com.brenda.courseflow.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public List<ParticipantResponse> findAll() {
        return participantRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ParticipantResponse findById(Long id) {
        Participant participant = getParticipantById(id);
        return mapToResponse(participant);
    }

    public ParticipantResponse create(ParticipantCreateRequest request) {
        if (participantRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Participant email already exists");
        }

        Participant participant = new Participant();
        participant.setFirstName(request.getFirstName());
        participant.setLastName(request.getLastName());
        participant.setEmail(request.getEmail());
        participant.setPhone(request.getPhone());
        participant.setInstitution(request.getInstitution());

        return mapToResponse(participantRepository.save(participant));
    }

    public Participant getParticipantById(Long id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found"));
    }

    public ParticipantResponse update(Long id, ParticipantUpdateRequest request) {
        Participant participant = getParticipantById(id);

        if (participantRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new BadRequestException("Participant email already exists");
        }

        participant.setFirstName(request.getFirstName());
        participant.setLastName(request.getLastName());
        participant.setEmail(request.getEmail());
        participant.setPhone(request.getPhone());
        participant.setInstitution(request.getInstitution());

        return mapToResponse(participantRepository.save(participant));
    }

    public void delete(Long id) {
        Participant participant = getParticipantById(id);
        participantRepository.delete(participant);
    }

    private ParticipantResponse mapToResponse(Participant participant) {
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