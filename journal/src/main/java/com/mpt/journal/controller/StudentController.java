package com.mpt.journal.controller;

import com.mpt.journal.model.StudentModel;
import com.mpt.journal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public String getAllStudents(Model model, 
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        List<StudentModel> students = studentService.getStudentsPage(page, size);
        long totalCount = studentService.getTotalStudentsCount();
        int totalPages = (int) Math.ceil((double) totalCount / size);
        
        model.addAttribute("students", students);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalCount", totalCount);
        return "studentList";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestParam String name,
                             @RequestParam String lastName,
                             @RequestParam String firstName,
                             @RequestParam String middleName) {
        StudentModel newStudent = new StudentModel(0, name, lastName, firstName, middleName); // тут мы получаем данные с главных полей, id задается автоматически из нашего репозитория
        studentService.addStudent(newStudent); // добавление студента в оперативную память(после перезапуска проекта, все данные стираются)
        return "redirect:/students"; // Здесь мы с вами используем redirect на наш GetMapping, чтобы не создавать много однотипных страниц, считай просто презагружаем страницу с новыми данными
    }

    @PostMapping("/students/update")
    public String updateStudent(@RequestParam int id,
                                @RequestParam String name,
                                @RequestParam String lastName,
                                @RequestParam String firstName,
                                @RequestParam String middleName) {
        StudentModel updatedStudent = new StudentModel(id, name, lastName, firstName, middleName); // Получаем новые данные из полей для обновления
        studentService.updateStudent(updatedStudent); // Ссылаемся на наш сервис для обновления по id
        return "redirect:/students"; // Здесь мы с вами используем redirect на наш GetMapping, чтобы не создавать много однотипных страниц, считай просто презагружаем страницу с новыми данными
    }

    @PostMapping("/students/delete")
    public String deleteStudent(@RequestParam int id) {
        studentService.deleteStudent(id); // Ссылаемся на наш сервис для удаления по id
        return "redirect:/students"; // Здесь мы с вами используем redirect на наш GetMapping, чтобы не создавать много однотипных страниц, считай просто презагружаем страницу с новыми данными
    }

    @PostMapping("/students/logical-delete")
    public String logicalDeleteStudent(@RequestParam int id) {
        studentService.logicalDeleteStudent(id);
        return "redirect:/students";
    }

    @PostMapping("/students/delete-multiple")
    public String deleteMultipleStudents(@RequestParam String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        studentService.deleteMultipleStudents(idList);
        return "redirect:/students";
    }

    @PostMapping("/students/logical-delete-multiple")
    public String logicalDeleteMultipleStudents(@RequestParam String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        studentService.logicalDeleteMultipleStudents(idList);
        return "redirect:/students";
    }

    @GetMapping("/students/search")
    public String searchStudents(@RequestParam(required = false) String name,
                                @RequestParam(required = false) String lastName,
                                @RequestParam(required = false) String firstName,
                                Model model) {
        List<StudentModel> students;
        
        if (name != null && !name.isEmpty()) {
            students = studentService.searchStudentsByName(name);
        } else if (lastName != null && !lastName.isEmpty()) {
            students = studentService.searchStudentsByLastName(lastName);
        } else if (firstName != null && !firstName.isEmpty()) {
            students = studentService.searchStudentsByFirstName(firstName);
        } else {
            students = studentService.findAllStudent();
        }
        
        model.addAttribute("students", students);
        model.addAttribute("searchName", name);
        model.addAttribute("searchLastName", lastName);
        model.addAttribute("searchFirstName", firstName);
        return "studentList";
    }

    @GetMapping("/students/filter")
    public String filterStudents(@RequestParam(required = false) Boolean isActive,
                                @RequestParam(required = false) Integer minNameLength,
                                @RequestParam(required = false) Integer maxNameLength,
                                @RequestParam(required = false) Integer minLastNameLength,
                                @RequestParam(required = false) Integer maxLastNameLength,
                                Model model) {
        List<StudentModel> students = studentService.findAllStudent();
        
        if (isActive != null) {
            students = studentService.filterStudentsByActiveStatus(isActive);
        }
        
        if (minNameLength != null && maxNameLength != null) {
            students = studentService.filterStudentsByNameLength(minNameLength, maxNameLength);
        }
        
        if (minLastNameLength != null && maxLastNameLength != null) {
            students = studentService.filterStudentsByLastNameLength(minLastNameLength, maxLastNameLength);
        }
        
        model.addAttribute("students", students);
        model.addAttribute("filterIsActive", isActive);
        model.addAttribute("filterMinNameLength", minNameLength);
        model.addAttribute("filterMaxNameLength", maxNameLength);
        model.addAttribute("filterMinLastNameLength", minLastNameLength);
        model.addAttribute("filterMaxLastNameLength", maxLastNameLength);
        return "studentList";
    }
}
