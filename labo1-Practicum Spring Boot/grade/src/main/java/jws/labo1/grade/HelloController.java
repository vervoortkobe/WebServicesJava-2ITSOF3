package jws.labo1.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jws.labo1.grade.jpa.Grade;
import jws.labo1.grade.jpa.GradeRepository;

@Controller
public class HelloController {

  @Autowired
  private GradeRepository repository;

  @GetMapping("/")
  public String home() {
    return "hello world";
  }
  
  @RequestMapping("/grades")
  public ResponseEntity<String> getGrades() {

    String grades = "<h2>All Grades</h2><ul>";

    try {
      //get all grades
      Iterable<Grade> itr = this.repository.findAll();
      for (Grade g : itr) {
          grades += "<li>" + g.getFirstName() + " - " + g.getLastName() + " - " + g.getGrade() + "</li>";
      }
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }

    return new ResponseEntity<>(grades + "</ul>", HttpStatus.OK);
  }

  @GetMapping("/error")
  public String error() {
    return "error";
  } 

  
}
