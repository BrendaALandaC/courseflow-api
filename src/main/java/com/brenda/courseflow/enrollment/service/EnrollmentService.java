package com.brenda.courseflow.enrollment.service;

import com.brenda.courseflow.course.entity.Course;
import com.brenda.courseflow.course.repository.CourseRepository;
import com.brenda.courseflow.enrollment.dto.EnrollmentAttendanceRequest;
import com.brenda.courseflow.enrollment.dto.EnrollmentCreateRequest;
import com.brenda.courseflow.enrollment.dto.EnrollmentGradeRequest;
import com.brenda.courseflow.enrollment.dto.EnrollmentResponse;
import com.brenda.courseflow.enrollment.entity.Enrollment;
import com.brenda.courseflow.enrollment.repository.EnrollmentRepository;
import com.brenda.courseflow.participant.entity.Participant;
import com.brenda.courseflow.participant.repository.ParticipantRepository;
import com.brenda.courseflow.shared.exception.BadRequestException;
import com.brenda.courseflow.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final ParticipantRepository participantRepository;

    public EnrollmentResponse create(EnrollmentCreateRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Participant participant = participantRepository.findById(request.getParticipantId())
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found"));

        if (!course.getActive()) {
            throw new BadRequestException("Course is not active");
        }

        if (enrollmentRepository.existsByCourseIdAndParticipantId(
                request.getCourseId(),
                request.getParticipantId()
        )) {
            throw new BadRequestException("Participant is already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setParticipant(participant);

        return mapToResponse(enrollmentRepository.save(enrollment));
    }

    public List<EnrollmentResponse> findByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found");
        }

        return enrollmentRepository.findByCourseId(courseId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<EnrollmentResponse> findByParticipantId(Long participantId) {
        if (!participantRepository.existsById(participantId)) {
            throw new ResourceNotFoundException("Participant not found");
        }

        return enrollmentRepository.findByParticipantId(participantId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Enrollment getEnrollmentById(Long id) {

        return enrollmentRepository.findWithDetailsById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment not found"
                        ));
    }

    public EnrollmentResponse findById(Long id) {

        return mapToResponse(
                getEnrollmentById(id)
        );
    }

    public EnrollmentResponse updateAttendance(
            Long id,
            EnrollmentAttendanceRequest request
    ) {

        Enrollment enrollment = getEnrollmentById(id);

        enrollment.setAttendanceConfirmed(
                request.getAttendanceConfirmed()
        );

        enrollmentRepository.save(enrollment);

        return mapToResponse(enrollment);
    }

    public EnrollmentResponse updateGrade(
            Long id,
            EnrollmentGradeRequest request
    ) {

        Enrollment enrollment = getEnrollmentById(id);

        enrollment.setGrade(
                request.getGrade()
        );

        enrollment.setApproved(
                request.getGrade() >= 7
        );

        enrollmentRepository.save(enrollment);

        return mapToResponse(enrollment);
    }

    public void delete(Long id) {

        Enrollment enrollment = getEnrollmentById(id);

        enrollmentRepository.delete(enrollment);
    }

    private EnrollmentResponse mapToResponse(Enrollment enrollment) {
        return EnrollmentResponse.builder()
                .id(enrollment.getId())
                .courseId(enrollment.getCourse().getId())
                .courseTitle(enrollment.getCourse().getTitle())
                .participantId(enrollment.getParticipant().getId())
                .participantName(
                        enrollment.getParticipant().getFirstName()
                                + " "
                                + enrollment.getParticipant().getLastName()
                )
                .attendanceConfirmed(enrollment.getAttendanceConfirmed())
                .grade(enrollment.getGrade())
                .approved(enrollment.getApproved())
                .certificateIssued(enrollment.getCertificateIssued())
                .build();
    }
}