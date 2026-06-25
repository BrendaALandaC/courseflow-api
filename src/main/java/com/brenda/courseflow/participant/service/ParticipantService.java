package com.brenda.courseflow.participant.service;

import com.brenda.courseflow.participant.dto.ParticipantCreateRequest;
import com.brenda.courseflow.participant.dto.ParticipantResponse;
import com.brenda.courseflow.participant.dto.ParticipantUpdateRequest;
import com.brenda.courseflow.participant.entity.Participant;
import com.brenda.courseflow.participant.repository.ParticipantRepository;
import com.brenda.courseflow.participant.mapper.ParticipantMapper;
import com.brenda.courseflow.shared.exception.BadRequestException;
import com.brenda.courseflow.shared.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.brenda.courseflow.shared.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    public PageResponse<ParticipantResponse> findAll(
            String name,
            Pageable pageable
    ) {

        Page<Participant> participantPage;

        if (name == null || name.isBlank()) {

            participantPage =
                    participantRepository.findAll(pageable);

        } else {

            participantPage =
                    participantRepository
                            .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                                    name,
                                    name,
                                    pageable
                            );
        }

        Page<ParticipantResponse> responsePage =
                participantPage.map(participantMapper::toResponse);

        return PageResponse.<ParticipantResponse>builder()
                .content(responsePage.getContent())
                .page(responsePage.getNumber())
                .size(responsePage.getSize())
                .totalElements(responsePage.getTotalElements())
                .totalPages(responsePage.getTotalPages())
                .last(responsePage.isLast())
                .build();
    }

    public ParticipantResponse findById(Long id) {
        Participant participant = getParticipantById(id);
        return participantMapper.toResponse(participant);
    }

    public ParticipantResponse create(ParticipantCreateRequest request) {
        if (participantRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Participant email already exists");
        }
        Participant participant = participantMapper.toEntity(request);

        return participantMapper.toResponse(participantRepository.save(participant));
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

        return participantMapper.toResponse(participantRepository.save(participant));
    }

    public void delete(Long id) {
        Participant participant = getParticipantById(id);
        participantRepository.delete(participant);
    }

}