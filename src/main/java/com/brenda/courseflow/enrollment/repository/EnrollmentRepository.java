package com.brenda.courseflow.enrollment.repository;

import com.brenda.courseflow.enrollment.entity.Enrollment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByCourseIdAndParticipantId(
            Long courseId,
            Long participantId
    );


    @EntityGraph(
            attributePaths = {
                    "course",
                    "participant"
            }
    )
    Optional<Enrollment> findWithDetailsById(Long id);

    @EntityGraph(
            attributePaths = {
                    "course",
                    "participant"
            }
    )
    List<Enrollment> findByCourseId(Long courseId);

    @EntityGraph(
            attributePaths = {
                    "course",
                    "participant"
            }
    )
    List<Enrollment> findByParticipantId(Long participantId);

}