package com.example.university.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.university.model.*;

public interface StudentRepository {
    ArrayList<Student> getStudents();

    Student getStudentById(int studentId);

    Student addStudent(Student student);

    Student updateStudent(int studentId, Student student);

    void deleteStudent(int studentId);

    List<Course> getStudentCourse(int studentId);
}

// Write your code here