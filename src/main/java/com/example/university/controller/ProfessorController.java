package com.example.university.controller;


import com.example.university.model.Course;
import com.example.university.model.Professor;
import com.example.university.service.ProfessorJpaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;


@RestController
public class ProfessorController {


    @Autowired
    private ProfessorJpaService professorJpaService;


    @GetMapping("/professors")
    public List<Professor> getProfessors(){
        return professorJpaService.getProfessors();
    }


    @GetMapping("/professors/{professorId}")
    public Professor getProfessorById(@PathVariable Integer professorId){
        return professorJpaService.getProfessorById(professorId);
    }


    @PostMapping("/professors")
    public Professor addProfessor(@RequestBody Professor professor){
        return professorJpaService.addProfessor(professor);
    }


    @PutMapping("/professors/{professorId}")
    public Professor updateProfessor(@RequestBody Professor professor, @PathVariable("professorId") int professorId) {
        return professorJpaService.updateProfessor(professorId, professor);
    }


    @DeleteMapping("/professors/{professorId}")
    public void deleteProfessor(@PathVariable("professorId") int professorId) {
        professorJpaService.deleteProfessor(professorId);
    }


    @GetMapping("/professors/{professorId}/courses")
    public List<Course> getProfessorCourses(@PathVariable("professorId") int professorId){
        return professorJpaService.getProfessorCourses(professorId);
    }
}