package com.mpt.journal.service;

import com.mpt.journal.entity.StudentEntity;
import com.mpt.journal.model.StudentModel;
import com.mpt.journal.repository.InMemoryStudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InMemoryStudentServiceImpl implements StudentService {

    private final InMemoryStudentRepository studentRepository;

    public InMemoryStudentServiceImpl(InMemoryStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentModel> findAllStudent() {
        return studentRepository.findAllStudents();
    }

    @Override
    public StudentModel findStudentById(int id) {
        return studentRepository.findStudentById(id);
    }

    @Override
    public StudentModel addStudent(StudentModel student) {
        return studentRepository.addStudent(student);
    }

    @Override
    public StudentModel updateStudent(StudentModel student) {
        return studentRepository.updateStudent(student);
    }

    @Override
    public void deleteStudent(int id) {
        studentRepository.deleteStudent(id);
    }

    @Override
    public List<StudentModel> findActiveStudents() {
        return studentRepository.findActiveStudents();
    }

    @Override
    public void logicalDeleteStudent(int id) {
        studentRepository.logicalDeleteStudent(id);
    }

    @Override
    public List<StudentModel> searchStudentsByName(String name) {
        return studentRepository.searchStudentsByName(name);
    }

    @Override
    public List<StudentModel> searchStudentsByLastName(String lastName) {
        return studentRepository.searchStudentsByLastName(lastName);
    }

    @Override
    public List<StudentModel> searchStudentsByFirstName(String firstName) {
        return studentRepository.searchStudentsByFirstName(firstName);
    }

    @Override
    public List<StudentModel> filterStudentsByActiveStatus(boolean isActive) {
        return studentRepository.filterStudentsByActiveStatus(isActive);
    }

    @Override
    public List<StudentModel> filterStudentsByNameLength(int minLength, int maxLength) {
        return studentRepository.filterStudentsByNameLength(minLength, maxLength);
    }

    @Override
    public List<StudentModel> filterStudentsByLastNameLength(int minLength, int maxLength) {
        return studentRepository.filterStudentsByLastNameLength(minLength, maxLength);
    }

    @Override
    public void deleteMultipleStudents(List<Integer> ids) {
        studentRepository.deleteMultipleStudents(ids);
    }

    @Override
    public void logicalDeleteMultipleStudents(List<Integer> ids) {
        studentRepository.logicalDeleteMultipleStudents(ids);
    }

    @Override
    public List<StudentModel> getStudentsPage(int page, int size) {
        return studentRepository.getStudentsPage(page, size);
    }

    @Override
    public long getTotalStudentsCount() {
        return studentRepository.getTotalStudentsCount();
    }
}
