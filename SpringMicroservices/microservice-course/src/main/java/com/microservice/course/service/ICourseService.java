package com.microservice.course.service;

import com.microservice.course.entities.Course;
import com.microservice.course.http.response.StudentByCourseResponse;

import java.util.List;

public interface ICourseService {
    List<Course> findAll();
    Course findById(Long id);
    void save(Course course);
    void deleteById(Long id);
    Course update(Long id, Course course);
    StudentByCourseResponse findStudentsByIdCourse(Long idCourse);
}
