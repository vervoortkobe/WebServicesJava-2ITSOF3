package edu.ap.spring.examen.repository;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private List<WebUser> users = new ArrayList<>();

    public List<WebUser> findAll() {
        return this.users;
    }

    public WebUser save(WebUser user) {
        this.users.add(user);
        return user;
    }
}
