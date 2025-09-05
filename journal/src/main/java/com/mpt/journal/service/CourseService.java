package com.mpt.journal.service;

import com.mpt.journal.model.CourseModel;

import java.util.List;

public interface CourseService {
    List<CourseModel> findAllCourses();
    List<CourseModel> findActiveCourses();
    CourseModel findCourseById(int id);
    CourseModel addCourse(CourseModel course);
    CourseModel updateCourse(CourseModel course);
    void deleteCourse(int id);
    void logicalDeleteCourse(int id);
    List<CourseModel> searchCoursesByName(String courseName);
    List<CourseModel> searchCoursesByInstructor(String instructor);
    List<CourseModel> filterCoursesByCredits(int minCredits, int maxCredits);
    void deleteMultipleCourses(List<Integer> ids);
    void logicalDeleteMultipleCourses(List<Integer> ids);
}
