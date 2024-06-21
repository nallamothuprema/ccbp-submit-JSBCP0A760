package com.example.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import com.example.university.model.*;
import com.example.university.repository.*;

@Service
public class CourseJpaService implements CourseRepository {

	@Autowired
	private CourseJpaRepository courseJpaRepository;

	@Autowired
	private StudentJpaRepository studentJpaRepository;

	@Autowired
	private ProfessorJpaRepository professorJpaRepository;

	@Override
	public ArrayList<Course> getCourses() {
		List<Course> courseList = courseJpaRepository.findAll();
		ArrayList<Course> courses = new ArrayList<>(courseList);
		return courses;
	}

	@Override
	public Course getCourseById(int courseId) {
		try {
			Course course = courseJpaRepository.findById(courseId).get();
			return course;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Course addCourse(Course course) {
		try {
			Professor professor = course.getProfessor();
			int professorId = professor.getProfessorId();

			Professor newProfessors = professorJpaRepository.findById(professorId).get();
			course.setProfessor(newProfessors);

			List<Student> students = course.getStudents();

			List<Integer> studentIds = new ArrayList<>();

			for (Student student : students) {
				studentIds.add(student.getStudentId());
			}

			List<Student> newStudent = studentJpaRepository.findAllById(studentIds);

			course.setStudents(newStudent);
			courseJpaRepository.save(course);
			return course;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong ProfessorId");
		}
	}

	@Override
	public Course updateCourse(int courseId, Course course) {
		try {
			Course newCourse = courseJpaRepository.findById(courseId).get();
			if (course.getCourseName() != null) {
				newCourse.setCourseName(course.getCourseName());
			}
			if (course.getCredits() != null) {
				newCourse.setCredits(course.getCredits());
			}
			if (course.getProfessor() != null) {
				Professor professor = course.getProfessor();
				int professorId = professor.getProfessorId();
				Professor newProfessors = professorJpaRepository.findById(professorId).get();
				newCourse.setProfessor(newProfessors);
			}
			if (course.getStudents() != null) {
				List<Student> students = course.getStudents();

				List<Integer> studentIds = new ArrayList<>();

				for (Student student : students) {
					studentIds.add(student.getStudentId());
				}

				List<Student> newStudent = studentJpaRepository.findAllById(studentIds);

				newCourse.setStudents(newStudent);
			}
			courseJpaRepository.save(newCourse);
			return newCourse;

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteCourse(int courseId) {
		try {
			courseJpaRepository.deleteById(courseId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);
	}

	@Override
	public List<Student> getCourseStudent(int courseId) {
		try {
			Course course = courseJpaRepository.findById(courseId).get();
			List<Student> students = course.getStudents();
			return students;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Professor getCourseProfessor(int courseId) {
		try {
			Course course = courseJpaRepository.findById(courseId).get();
			Professor professor = course.getProfessor();
			return professor;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}

// Write your code here