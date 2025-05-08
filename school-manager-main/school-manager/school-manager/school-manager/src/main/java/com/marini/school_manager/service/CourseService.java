package com.marini.school_manager.service;

import com.marini.school_manager.exception.ResourceNotFoundException;
import com.marini.school_manager.model.Course;
import com.marini.school_manager.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Page<Course> getAllCourse(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course with Id: " + id + " not found"));
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Course with Id: " + id + " not found");
        }
    }

    public Course updateCourse(Long id, Course course) {
        Optional<Course> existingCourseOptional = courseRepository.findById(id);

        if (!existingCourseOptional.isPresent()) {
            throw new ResourceNotFoundException("Course with Id: " + id + " not found");
        }

        Course existingCourse = existingCourseOptional.get();
        existingCourse.setDescription(course.getDescription());
        existingCourse.setEnrollements(course.getEnrollements());
        existingCourse.setTeachers(course.getTeachers());
        existingCourse.setTitle(course.getTitle());
        existingCourse.setStartDate(course.getStartDate());
        existingCourse.setEndDate(course.getEndDate());

        return courseRepository.save(existingCourse);
    }
}
