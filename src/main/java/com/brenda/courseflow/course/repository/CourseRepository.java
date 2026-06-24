package com.brenda.courseflow.course.repository;

import com.brenda.courseflow.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByActive(Boolean active, Pageable pageable);

}