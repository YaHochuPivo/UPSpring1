package com.mpt.journal.repository;

import com.mpt.journal.model.StudentModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Repository

public class InMemoryStudentRepository {
    private List<StudentModel> students = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1); // Генерация уникального ID

    public StudentModel addStudent(StudentModel student) {
        student.setId(idCounter.getAndIncrement()); // Установка уникального ID
        students.add(student);
        return student;
    }

    public StudentModel updateStudent(StudentModel student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == student.getId()) {
                students.set(i, student);
                return student;
            }
        }
        return null; // Студент не найден
    }

    public void deleteStudent(int id) {
        students.removeIf(student -> student.getId() == id);
    }

    public void logicalDeleteStudent(int id) {
        students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .ifPresent(student -> student.setActive(false));
    }

    public List<StudentModel> findAllStudents() {
        return new ArrayList<>(students);
    }

    public StudentModel findStudentById(int id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<StudentModel> findActiveStudents() {
        return students.stream()
                .filter(StudentModel::isActive)
                .toList();
    }

    public List<StudentModel> searchStudentsByName(String name) {
        return students.stream()
                .filter(student -> student.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    public List<StudentModel> searchStudentsByLastName(String lastName) {
        return students.stream()
                .filter(student -> student.getLastName().toLowerCase().contains(lastName.toLowerCase()))
                .toList();
    }

    public List<StudentModel> searchStudentsByFirstName(String firstName) {
        return students.stream()
                .filter(student -> student.getFirstName().toLowerCase().contains(firstName.toLowerCase()))
                .toList();
    }

    public List<StudentModel> filterStudentsByActiveStatus(boolean isActive) {
        return students.stream()
                .filter(student -> student.isActive() == isActive)
                .toList();
    }

    public List<StudentModel> filterStudentsByNameLength(int minLength, int maxLength) {
        return students.stream()
                .filter(student -> student.getName().length() >= minLength && student.getName().length() <= maxLength)
                .toList();
    }

    public List<StudentModel> filterStudentsByLastNameLength(int minLength, int maxLength) {
        return students.stream()
                .filter(student -> student.getLastName().length() >= minLength && student.getLastName().length() <= maxLength)
                .toList();
    }

    public void deleteMultipleStudents(List<Integer> ids) {
        students.removeIf(student -> ids.contains(student.getId()));
    }

    public void logicalDeleteMultipleStudents(List<Integer> ids) {
        students.stream()
                .filter(student -> ids.contains(student.getId()))
                .forEach(student -> student.setActive(false));
    }

    public List<StudentModel> getStudentsPage(int page, int size) {
        int start = page * size;
        int end = Math.min(start + size, students.size());
        if (start >= students.size()) {
            return new ArrayList<>();
        }
        return students.subList(start, end);
    }

    public long getTotalStudentsCount() {
        return students.size();
    }
}
