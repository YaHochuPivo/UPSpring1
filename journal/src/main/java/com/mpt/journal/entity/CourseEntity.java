package com.mpt.journal.entity;

import com.mpt.journal.model.CourseModel;

public class CourseEntity extends CourseModel {

    public CourseEntity(int id, String courseName, String description, int credits, String instructor, boolean isActive) {
        super(id, courseName, description, credits, instructor, isActive);
    }
}
