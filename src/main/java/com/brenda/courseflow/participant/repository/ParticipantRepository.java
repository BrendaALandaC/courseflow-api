package com.brenda.courseflow.participant.repository;

import com.brenda.courseflow.participant.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    boolean existsByEmail(String email);
}