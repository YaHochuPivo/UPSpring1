package com.mpt.journal.service;


import com.mpt.journal.entity.StudentEntity;
import com.mpt.journal.model.StudentModel;

import java.util.List;

public interface StudentService {
    public List<StudentModel> findAllStudent();
    public List<StudentModel> findActiveStudents();
    public StudentModel findStudentById(int id);
    public StudentModel addStudent(StudentModel student);
    public StudentModel updateStudent(StudentModel student);
    public void deleteStudent(int id);
    public void logicalDeleteStudent(int id);
    public List<StudentModel> searchStudentsByName(String name);
    public List<StudentModel> searchStudentsByLastName(String lastName);
    public List<StudentModel> searchStudentsByFirstName(String firstName);
    public List<StudentModel> filterStudentsByActiveStatus(boolean isActive);
    public List<StudentModel> filterStudentsByNameLength(int minLength, int maxLength);
    public List<StudentModel> filterStudentsByLastNameLength(int minLength, int maxLength);
    public void deleteMultipleStudents(List<Integer> ids);
    public void logicalDeleteMultipleStudents(List<Integer> ids);
    public List<StudentModel> getStudentsPage(int page, int size);
    public long getTotalStudentsCount();
}
