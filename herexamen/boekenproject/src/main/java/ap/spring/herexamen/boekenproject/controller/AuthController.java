package ap.spring.herexamen.boekenproject.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ap.spring.herexamen.boekenproject.jpa.WebUser;
import ap.spring.herexamen.boekenproject.jpa.WebUserRepository;

import static ap.spring.herexamen.boekenproject.security.WebSecurityConfig.reloadUsers;

@Controller
public class AuthController {

    @Autowired
    private WebUserRepository repo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Qualifier("users")
    @Autowired
    private UserDetailsService users;

    @PostConstruct
    public void postConstruct() {
        WebUser admin = new WebUser("admin", passwordEncoder.encode("pwd123"), "WEBUSER");
        repo.save(admin);
    }

    @GetMapping("/")
    public String getIndex() {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String getUsers() {
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        WebUser found = repo.findByUsername(username);

        if (found != null)
            return "redirect:/register?exists";

        repo.save(new WebUser(username, passwordEncoder.encode(password), "WEBUSER"));
        System.out.println("Register: New user: " + username + ", " + password + ", WEBUSER");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin() {
        reloadUsers(repo);
        return "login";
    }

    /*@PostMapping("/login")
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        WebUser found = repo.findByUsernameAndPassword(username, passwordEncoder.encode(password));

        if (found == null)
            return "exists";

        System.out.println("Login: User: " + username + ", " + password);
        return "redirect:/home";
    }*/

    @GetMapping("/logout")
    public String login() {
        return "redirect:/login";
    }
}
