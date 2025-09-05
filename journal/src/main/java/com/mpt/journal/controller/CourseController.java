package com.mpt.journal.controller;

import com.mpt.journal.model.CourseModel;
import com.mpt.journal.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public String getAllCourses(Model model, 
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        List<CourseModel> courses = courseService.findAllCourses();
        int totalPages = (int) Math.ceil((double) courses.size() / size);
        
        model.addAttribute("courses", courses);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        return "courseList";
    }

    @PostMapping("/courses/add")
    public String addCourse(@RequestParam String courseName,
                           @RequestParam String description,
                           @RequestParam int credits,
                           @RequestParam String instructor) {
        CourseModel newCourse = new CourseModel(0, courseName, description, credits, instructor, true);
        courseService.addCourse(newCourse);
        return "redirect:/courses";
    }

    @PostMapping("/courses/update")
    public String updateCourse(@RequestParam int id,
                              @RequestParam String courseName,
                              @RequestParam String description,
                              @RequestParam int credits,
                              @RequestParam String instructor) {
        CourseModel updatedCourse = new CourseModel(id, courseName, description, credits, instructor, true);
        courseService.updateCourse(updatedCourse);
        return "redirect:/courses";
    }

    @PostMapping("/courses/delete")
    public String deleteCourse(@RequestParam int id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }

    @PostMapping("/courses/logical-delete")
    public String logicalDeleteCourse(@RequestParam int id) {
        courseService.logicalDeleteCourse(id);
        return "redirect:/courses";
    }

    @PostMapping("/courses/delete-multiple")
    public String deleteMultipleCourses(@RequestParam String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        courseService.deleteMultipleCourses(idList);
        return "redirect:/courses";
    }

    @PostMapping("/courses/logical-delete-multiple")
    public String logicalDeleteMultipleCourses(@RequestParam String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        courseService.logicalDeleteMultipleCourses(idList);
        return "redirect:/courses";
    }

    @GetMapping("/courses/search")
    public String searchCourses(@RequestParam(required = false) String courseName,
                               @RequestParam(required = false) String instructor,
                               Model model) {
        List<CourseModel> courses;
        
        if (courseName != null && !courseName.isEmpty()) {
            courses = courseService.searchCoursesByName(courseName);
        } else if (instructor != null && !instructor.isEmpty()) {
            courses = courseService.searchCoursesByInstructor(instructor);
        } else {
            courses = courseService.findAllCourses();
        }
        
        model.addAttribute("courses", courses);
        model.addAttribute("searchCourseName", courseName);
        model.addAttribute("searchInstructor", instructor);
        return "courseList";
    }

    @GetMapping("/courses/filter")
    public String filterCourses(@RequestParam(required = false) Integer minCredits,
                               @RequestParam(required = false) Integer maxCredits,
                               @RequestParam(required = false) Boolean isActive,
                               Model model) {
        List<CourseModel> courses = courseService.findAllCourses();
        
        if (minCredits != null && maxCredits != null) {
            courses = courseService.filterCoursesByCredits(minCredits, maxCredits);
        }
        
        if (isActive != null) {
            courses = courses.stream()
                    .filter(course -> course.isActive() == isActive)
                    .collect(Collectors.toList());
        }
        
        model.addAttribute("courses", courses);
        model.addAttribute("filterMinCredits", minCredits);
        model.addAttribute("filterMaxCredits", maxCredits);
        model.addAttribute("filterIsActive", isActive);
        return "courseList";
    }
}
