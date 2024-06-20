package edu.ap.spring.websecurity.jpa;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<WebUser, Long> { 
}