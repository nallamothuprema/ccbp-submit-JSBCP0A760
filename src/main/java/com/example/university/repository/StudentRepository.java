package com.example.university.repository;


import com.example.university.model.Course;
import com.example.university.model.Student;


import java.util.List;


public interface StudentRepository {
    List<Student> getStudents();


    Student getStudentById(int studentId);


    Student addStudent(Student studentId);


    Student updateStudent(int studentId, Student student);


    void deleteStudent(int studentId);


    List<Course> getStudentCourses(int studentId);
}

// Write your code here