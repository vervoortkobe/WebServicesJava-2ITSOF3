package edu.ap.spring.examen.controller;

import edu.ap.spring.examen.aop.Interceptable;
import edu.ap.spring.examen.redis.RedisService;

import edu.ap.spring.examen.repository.UserRepository;
import edu.ap.spring.examen.repository.WebUser;
import edu.ap.spring.examen.security.WebSecurityConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private RedisService service;

    @Autowired
    private UserRepository repo;

    @Autowired
    private WebSecurityConfig wsConfig;

    /*
     * Aanmaken van een default admin gebruiker voor security.
     */
    @PostConstruct
    private void defaultUser() {
        String encryptedPwd = wsConfig.passwordEncoder().encode("@dmin");
        WebUser user = new WebUser("admin", encryptedPwd);
        repo.save(user);
        System.out.println("user");
    }

    @GetMapping(value = "/")
    public String index() {
        return "redirect:/home";
    }

    /*
     * 3/ Na het inloggen, wordt de admin gebruiker omgeleid naar de homepage. 
     */
    @GetMapping(value = "/home")
    public String home(Model model) {
        var all = service.hgetAll("*");
        List<String> movies = new ArrayList<>();
        all.forEach((k, v) -> movies.add(v.toString()));
        // Hier worden alle namen van de films uit de Redis database alfabetisch opgelijst (3 punten).
        Collections.sort(movies);
        model.addAttribute("movies", all);
        return "home";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    /*
     * 4/ Voorzie een tekstveld en een knop op de homepage om een film te verwijderen op basis van de naam.  
     * Hierna wordt de homepage opnieuw ingeladen met de lijst van films (2 punten).
     */
    @Interceptable
    @PostMapping(value = "/delete")
    public String delete(@RequestParam("movie") String name) {
        service.delete(name);
        return "redirect:/home";
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }
    
    /*
     * 5/ Voorzie een zoekscherm waar je op acteur kunt zoeken en alle films waar hij in speelt opgelijst worden. 
     * Voorzie ook een zoekfunctie op jaar (3 punten).
     */
    @PostMapping("/search")
    public String moviesWithActor(@RequestParam("value") String value, Model model) throws JsonParseException {
        /*
         * Aangezien we een wildcard gebruiken die op alles filtert en gewoon kijkt of het doorgegeven value in de key zit, kan deze methode zowel zoeken op acteur als op jaar.
         */
        model.addAttribute("movies", service.hgetAll("*" + value + "*"));
        return "search";
    }
}
