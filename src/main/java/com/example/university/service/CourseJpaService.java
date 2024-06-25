package com.example.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.example.university.model.*;
import com.example.university.repository.*;
import com.example.university.service.*;

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

		int professorId = course.getProfessor().getProfessorId();
		Professor professor = professorJpaRepository.findById(professorId).get();
		course.setProfessor(professor);

		List<Integer> studentIds = new ArrayList<>();
		for (Student student : course.getStudents()) {
			studentIds.add(student.getStudentId());
		}

		List<Student> students = studentJpaRepository.findAllById(studentIds);
		course.setStudents(students);

		return courseJpaRepository.save(course);
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
				int professorId = course.getProfessor().getProfessorId();
				Professor professor = professorJpaRepository.findById(professorId).get();
				newCourse.setProfessor(professor);
			}

			List<Integer> studentIds = new ArrayList<>();
			for (Student student : course.getStudents()) {
				studentIds.add(student.getStudentId());
			}

			List<Student> students = studentJpaRepository.findAllById(studentIds);
			newCourse.setStudents(students);

			return courseJpaRepository.save(newCourse);

		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong professorId");
		}
	}

	@Override
	public void deleteCourse(int courseId) {
		try {
			Course course = courseJpaRepository.findById(courseId).get();
			List<Student> students = course.getStudents();
			for (Student student : students) {
				student.getCourses().remove(course);
			}

			studentJpaRepository.saveAll(students);
			courseJpaRepository.deleteById(courseId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "courseId" + courseId + " not found");
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);
	}

	@Override
	public Professor getCourseProfessor(int courseId) {
		try {
			Course course = courseJpaRepository.findById(courseId).get();
			return course.getProfessor();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public List<Student> getCourseStudent(int courseId) {
		try {
			Course course = courseJpaRepository.findById(courseId).get();
			return course.getStudents();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
