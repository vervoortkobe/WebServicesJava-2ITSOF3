package com.examen.studenten.controller;

import com.examen.studenten.jpa.Student;
import com.examen.studenten.jpa.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository repository;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/")
    public String index() {
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("students", sortedStudents());

        return "list";
    }

    @GetMapping("/student")
    public String student() {
        return "studentForm";
    }

    @PostMapping("/student")
    public String saveStudent(@RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String dateOfBirth, @RequestParam String studyProgram) {

        // String year = dateOfBirth.split("-")[0];
        // String month = dateOfBirth.split("-")[1];
        // String dayOfMonth = dateOfBirth.split("-")[2];
        // System.out.println(year + " " + month + " " + dayOfMonth);
        String student = lastName + " " + firstName + " " + dateOfBirth + " " + studyProgram;
        if(!studentExists(student)) repository.save(new Student(firstName, lastName, formatToDate(dateOfBirth), studyProgram));

        return "redirect:/list";
    }

    private ArrayList<String> sortedStudents() {
        //create studentlist
        ArrayList<String> studentList = new ArrayList<>();

        // add each student
        Long i = Long.parseLong(1 + "");
        for(; i <= studentRepository.count(); i++){
            String student =  studentRepository.findById(i).get().getLastName() + " "
                    + studentRepository.findById(i).get().getFirstName() + " "
                    + studentRepository.findById(i).get().getDateOfBirth().toString() + " "
                    + studentRepository.findById(i).get().getStudyProgram();
            studentList.add(student);
        }
        //sort list
        Collections.sort(studentList);
        return studentList;
    }

    private LocalDate formatToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            return LocalDate.parse(date, formatter);
        }catch(Exception e){
            return null;
        }
    }

    //check if student exists
    private boolean studentExists(String studentCheck){
        for (String stud : sortedStudents()) {
            if(studentCheck.equalsIgnoreCase(stud)){
                return true;
            }
        }
        return false;
    }
}
