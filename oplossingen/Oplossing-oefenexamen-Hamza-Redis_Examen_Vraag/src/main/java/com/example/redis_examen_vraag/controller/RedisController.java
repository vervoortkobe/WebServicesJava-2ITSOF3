package com.example.redis_examen_vraag.controller;

import com.example.redis_examen_vraag.RedisService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class RedisController {

    @Autowired
    private RedisService service;

    List<String> allmovies = new ArrayList<>();
    boolean antwoord = false;
    @PostConstruct
    public void init() {
        // empty db
        this.service.flushDb();
        List<String> movies = new ArrayList<>();
        movies.add("movies:The Big Lebowski:Jef Bridges, raf boeizan, bayram, zebi:1997");
        movies.add("movies:Papillon:hamza, najib, leandro:1995");
        movies.add("movies:Fast And The furious:hamza, najib, leandro:1991");
        movies.add("movies:Madagaskar:hamza, najib, leandro:1998");
        movies.add("movies:Rft:hamza, najib, leandro:2010");
        movies.add("movies:Narcos:hamza, najib, leandro:2002");
        movies.add("movies:Zebiii:hamza, najib, leandro:1995");
        for (String s:movies) {
            Map<String,String> map = new HashMap<>();
            String year = s.split(":")[3];
            String actors = s.split(":")[2];
            map.put(actors,year);
            String title = "movies:" + s.split(":")[1];
            this.service.hset(title,map);
        }
        // movies
    }

    @GetMapping("/")
    public String index() {
        return "menu";
    }
    @GetMapping("/movies")
    public String movies(Model model) {
        Set<String> movies = this.service.keys("movies:*");
        String actors = "";
        Object year= null;
        for (String s:movies) {
            Map<Object,Object> map = this.service.hgetAll(s);
            for (Map.Entry<Object,Object> entry : map.entrySet()) {
                actors = entry.getKey().toString();
                year = entry.getValue();
                allmovies.add(s.split(":")[1] + ":" + actors + ": Year -" + year.toString() + "");
            }
        }
        if (antwoord == true){
            Collections.sort(allmovies);
        }else if (antwoord == false){
            allmovies.sort((a,b) -> Integer.compare(Integer.parseInt(a.split("-")[1]), Integer.parseInt(b.split("-")[1])));
        }
        model.addAttribute("movies", allmovies);

        return "movies";
    }

    @GetMapping("/sorteer")
    public String sorteer() {
        return "sorteer";
    }
    @PostMapping("/sorteer")
    public String postMessage(@RequestParam("sorteer") String sorteer) {
        if (sorteer.toLowerCase().equals("a")) {
            antwoord = true;
        }
        else if (sorteer.toLowerCase().equals("b")){
            antwoord = false;
        }
        else {
            System.out.println("Je hebt geen a of b ingevuld");
        }
        return "redirect:/movies";
    }
    @GetMapping("/zoek")
    public String zoek() {
        return "zoek";
    }
    @PostMapping("/zoek")
    public String postfilm(@RequestParam("zoek") String sorteer, Model model) {
        for (String s:allmovies) {
            if (s.split(":")[0].equals(sorteer)) {
                String actors = s.split(":")[1];
                model.addAttribute("actors",actors);
            }
        }
        return "actors";
    }
    @GetMapping("/zoekactor")
    public String zoekactor() {
        return "zoekactor";
    }
    @PostMapping("/zoekactor")
    public String zoekactor(@RequestParam("zoekactor") String sorteer, Model model) {
        List<String> filmsactor = new ArrayList<>();
        for (String s:allmovies) {
            String actors = s.split(":")[1];
            for (String actor:actors.split(", ")) {
                if (actor.equals(sorteer)) {
                    filmsactor.add(s.split(":")[0]);
                }
            }
        }
        model.addAttribute("films",filmsactor);
        return "filmactor";
    }
}
