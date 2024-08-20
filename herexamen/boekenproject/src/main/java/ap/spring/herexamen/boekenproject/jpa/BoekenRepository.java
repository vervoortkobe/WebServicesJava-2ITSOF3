package ap.spring.herexamen.boekenproject.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoekenRepository extends CrudRepository<Boek, Long> {
    public List<Boek> findByAuthor(String author);

    public Boek findByTitle(String title);
}