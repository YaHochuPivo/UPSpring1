package com.mpt.journal.service;

import com.mpt.journal.model.CourseModel;
import com.mpt.journal.repository.InMemoryCourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryCourseServiceImpl implements CourseService {

    private final InMemoryCourseRepository courseRepository;

    public InMemoryCourseServiceImpl(InMemoryCourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseModel> findAllCourses() {
        return courseRepository.findAllCourses();
    }

    @Override
    public List<CourseModel> findActiveCourses() {
        return courseRepository.findActiveCourses();
    }

    @Override
    public CourseModel findCourseById(int id) {
        return courseRepository.findCourseById(id);
    }

    @Override
    public CourseModel addCourse(CourseModel course) {
        return courseRepository.addCourse(course);
    }

    @Override
    public CourseModel updateCourse(CourseModel course) {
        return courseRepository.updateCourse(course);
    }

    @Override
    public void deleteCourse(int id) {
        courseRepository.deleteCourse(id);
    }

    @Override
    public void logicalDeleteCourse(int id) {
        courseRepository.logicalDeleteCourse(id);
    }

    @Override
    public List<CourseModel> searchCoursesByName(String courseName) {
        return courseRepository.searchCoursesByName(courseName);
    }

    @Override
    public List<CourseModel> searchCoursesByInstructor(String instructor) {
        return courseRepository.searchCoursesByInstructor(instructor);
    }

    @Override
    public List<CourseModel> filterCoursesByCredits(int minCredits, int maxCredits) {
        return courseRepository.filterCoursesByCredits(minCredits, maxCredits);
    }

    @Override
    public void deleteMultipleCourses(List<Integer> ids) {
        courseRepository.deleteMultipleCourses(ids);
    }

    @Override
    public void logicalDeleteMultipleCourses(List<Integer> ids) {
        courseRepository.logicalDeleteMultipleCourses(ids);
    }
}
