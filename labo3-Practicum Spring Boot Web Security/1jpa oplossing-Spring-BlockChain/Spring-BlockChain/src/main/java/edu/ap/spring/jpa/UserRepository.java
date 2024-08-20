package edu.ap.spring.jpa;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<WebUser, Long> {
}