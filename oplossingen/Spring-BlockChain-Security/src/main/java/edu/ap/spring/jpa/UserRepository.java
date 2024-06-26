package edu.ap.spring.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<WebUser, Long> { 
}