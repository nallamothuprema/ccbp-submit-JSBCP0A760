package com.example.university.repository;


import com.example.university.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface StudentJpaRepository extends JpaRepository<Student,Integer> {
}

// Write your code here