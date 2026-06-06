package com.brenda.courseflow.course.repository;

import com.brenda.courseflow.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}