package com.example.databasetest.dao;

import com.example.databasetest.model.Student;

import java.util.List;

public interface StudentDao {
    long insert(Student student);
    List<Student> findAll();
    Student findById(long id);
    int update(long id, Student student);
    int deleteById(int id);

}
