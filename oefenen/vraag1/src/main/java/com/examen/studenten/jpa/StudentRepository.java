package com.examen.studenten.jpa;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {

    public Student findByFirstNameAndLastName(String firstName, String lastName);
}