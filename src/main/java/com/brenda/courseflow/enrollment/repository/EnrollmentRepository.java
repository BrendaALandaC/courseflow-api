package com.brenda.courseflow.enrollment.repository;

import com.brenda.courseflow.enrollment.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByCourseIdAndParticipantId(
            Long courseId,
            Long participantId
    );

    List<Enrollment> findByCourseId(Long courseId);

    List<Enrollment> findByParticipantId(Long participantId);
}