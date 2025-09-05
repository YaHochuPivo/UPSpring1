package com.mpt.journal.repository;

import com.mpt.journal.model.CourseModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryCourseRepository {
    private List<CourseModel> courses = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);

    public CourseModel addCourse(CourseModel course) {
        course.setId(idCounter.getAndIncrement());
        courses.add(course);
        return course;
    }

    public CourseModel updateCourse(CourseModel course) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId() == course.getId()) {
                courses.set(i, course);
                return course;
            }
        }
        return null;
    }

    public void deleteCourse(int id) {
        courses.removeIf(course -> course.getId() == id);
    }

    public void logicalDeleteCourse(int id) {
        courses.stream()
                .filter(course -> course.getId() == id)
                .findFirst()
                .ifPresent(course -> course.setActive(false));
    }

    public List<CourseModel> findAllCourses() {
        return new ArrayList<>(courses);
    }

    public List<CourseModel> findActiveCourses() {
        return courses.stream()
                .filter(CourseModel::isActive)
                .toList();
    }

    public CourseModel findCourseById(int id) {
        return courses.stream()
                .filter(course -> course.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<CourseModel> searchCoursesByName(String courseName) {
        return courses.stream()
                .filter(course -> course.getCourseName().toLowerCase().contains(courseName.toLowerCase()))
                .toList();
    }

    public List<CourseModel> searchCoursesByInstructor(String instructor) {
        return courses.stream()
                .filter(course -> course.getInstructor().toLowerCase().contains(instructor.toLowerCase()))
                .toList();
    }

    public List<CourseModel> filterCoursesByCredits(int minCredits, int maxCredits) {
        return courses.stream()
                .filter(course -> course.getCredits() >= minCredits && course.getCredits() <= maxCredits)
                .toList();
    }

    public void deleteMultipleCourses(List<Integer> ids) {
        courses.removeIf(course -> ids.contains(course.getId()));
    }

    public void logicalDeleteMultipleCourses(List<Integer> ids) {
        courses.stream()
                .filter(course -> ids.contains(course.getId()))
                .forEach(course -> course.setActive(false));
    }
}
