package com.example.university.repository;


import com.example.university.model.Course;
import com.example.university.model.Professor;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface ProfessorRepository {


    List<Professor> getProfessors();


    Professor getProfessorById(Integer professorId);


    Professor addProfessor(Professor professor);


    Professor updateProfessor(int professorId, Professor professor);


    void deleteProfessor(int professorId);


    List<Course> getProfessorCourses(int professorId);
}

// Write your code here