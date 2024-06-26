package edu.ap.spring.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class WebUser {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String userName;

	@Column
    private String password;

	public WebUser() {}
    
    public WebUser(String userName, String password) {
	    this.userName = userName;
	    this.password = password;
    }
    
    public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
