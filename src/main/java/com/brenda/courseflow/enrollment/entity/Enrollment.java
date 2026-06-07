package com.brenda.courseflow.enrollment.entity;


import com.brenda.courseflow.audit.entity.AuditableEntity;
import com.brenda.courseflow.course.entity.Course;
import com.brenda.courseflow.participant.entity.Participant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "enrollments")
public class Enrollment extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @Column(nullable = false)
    private Boolean attendanceConfirmed = false;

    private Double grade;

    @Column(nullable = false)
    private Boolean approved = false;

    private String certificateFolio;

    @Column(nullable = false)
    private Boolean certificateIssued = false;
}
