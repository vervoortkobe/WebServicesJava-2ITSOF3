package edu.ap.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.ap.spring.jpa.Grade;
import edu.ap.spring.jpa.GradeRepository;

@SpringBootApplication
public class RestApplication implements CommandLineRunner{

	@Autowired
	private GradeRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		Grade grade1 = new Grade("Philippe", "Possemiers", 16);
	 	repository.save(grade1);
	 	Grade grade2 = new Grade("Bruno", "Herman", 17);
	 	repository.save(grade2);
  	}
}
