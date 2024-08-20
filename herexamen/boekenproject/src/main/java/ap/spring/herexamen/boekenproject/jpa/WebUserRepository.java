package ap.spring.herexamen.boekenproject.jpa;

import org.springframework.data.repository.CrudRepository;

public interface WebUserRepository extends CrudRepository<WebUser, Long> {

    public WebUser findByUsername(String username);

    public WebUser findByUsernameAndPassword(String username, String password);
}