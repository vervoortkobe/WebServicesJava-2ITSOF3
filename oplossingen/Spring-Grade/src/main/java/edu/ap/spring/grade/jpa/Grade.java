package edu.ap.spring.grade.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Grade {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String firstName;

	@Column
    private String lastName;

    @Column
    private int grade;

	public Grade() {}
    
    public Grade(String firstName, String lastName, int grade) {
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.grade = grade;
    }
    
    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}	
}
