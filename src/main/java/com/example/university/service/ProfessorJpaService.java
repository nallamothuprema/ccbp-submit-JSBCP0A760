package com.example.university.service;


import com.example.university.model.Course;
import com.example.university.model.Professor;
import com.example.university.repository.CourseJpaRepository;
import com.example.university.repository.ProfessorJpaRepository;
import com.example.university.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;


@Service
public class ProfessorJpaService implements ProfessorRepository {


    @Autowired
    private ProfessorJpaRepository professorJpaRepository;


    @Autowired
    private CourseJpaRepository courseJpaRepository;


    @Override
    public List<Professor> getProfessors() {
        return professorJpaRepository.findAll();
    }


    @Override
    public Professor getProfessorById(Integer professorId) {
        try{
            return professorJpaRepository.findById(professorId).get();
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "professorId " + professorId + " not found");
        }
    }


    @Override
    public Professor addProfessor(Professor professor) {
        return professorJpaRepository.save(professor);
    }


    @Override
    public Professor updateProfessor(int professorId, Professor professor) {
        try {
            Professor newProfessor = professorJpaRepository.findById(professorId).get();
            if (professor.getProfessorName() != null) {
                newProfessor.setProfessorName(professor.getProfessorName());
            }
            if (professor.getDepartment() != null) {
                newProfessor.setDepartment(professor.getDepartment());
            }
            return professorJpaRepository.save(newProfessor);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "professorId " + professorId + " not found");
        }
    }


    @Override
    public void deleteProfessor(int professorId) {
        try {
            Professor professor = professorJpaRepository.findById(professorId).get();
            List<Course> courses = courseJpaRepository.findByProfessor(professor);
            for (Course course : courses) {
                course.setProfessor(null);
            }
            courseJpaRepository.saveAll(courses);
            professorJpaRepository.deleteById(professorId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "professorId " + professorId + " not found");
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
    
    @Override
    public List<Course> getProfessorCourses(int professorId) {
        try {
            Professor professor = professorJpaRepository.findById(professorId).get();
            return courseJpaRepository.findByProfessor(professor);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "professorId " + professorId + " not found");
        }
    }
}