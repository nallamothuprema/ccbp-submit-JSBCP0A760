package com.example.university.repository;

import java.util.ArrayList;
import java.util.List;
import com.example.university.model.*;

public interface ProfessorRepository {
    ArrayList<Professor> getProfessors();

    Professor getProfessorById(int professorId);

    Professor addProfessor(Professor professor);

    Professor updateProfessor(int professorId, Professor professor);

    void deleteProfessor(int professorId);

    List<Course> getProfessorCourse(int professorId);
}

// Write your code here