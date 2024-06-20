package edu.ap.spring.jpa;

import java.util.List;	
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="grades", path="grades")
public interface GradeRepository extends CrudRepository<Grade, Long> {
	List<Grade> findByLastName(@Param("lastName") String lastName);
}
