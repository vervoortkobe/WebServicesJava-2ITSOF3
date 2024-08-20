package oef1.fullproject.controller;

import oef1.fullproject.redis.RedisService;
import oef1.fullproject.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private RedisService redisService;

    @GetMapping
    public String getAllMovies(@RequestParam(name = "sort", defaultValue = "title") String sort,
                               @RequestParam(name = "search", defaultValue = "") String search,
                               Model model) {
                                
        List<Movie> movies = retrieveAllMovies();
        movies = filterMovies(movies, search);
        movies = sortMovies(movies, sort);

        model.addAttribute("movies", movies);
        model.addAttribute("sort", sort); // Add current sort parameter to model
        model.addAttribute("search", search); // Add current search parameter to model
        return "movies";
    }

    @PostMapping("/delete")
    public String deleteMovie(@RequestParam String title) {
        String redisKey = "movie:" + title.replaceAll(" ", "_");
        if (redisService.hasKey(redisKey)) {
            redisService.delete(redisKey);
        }
        return "redirect:/movies";
    }

    // Retrieve all movies from Redis
    private List<Movie> retrieveAllMovies() {
        Set<String> keys = redisService.keys("movie:*");
        List<Movie> movies = new ArrayList<>();
        for (String key : keys) {
            Map<Object, Object> movieMap = redisService.hgetAll(key);
            if (!movieMap.isEmpty()) {
                String title = (String) movieMap.get("title");
                String actors = (String) movieMap.get("actors");
                int year = Integer.parseInt((String) movieMap.get("year"));
                movies.add(new Movie(title, actors, year));
            }
        }
        return movies;
    }

    // Filter movies based on the search query
    private List<Movie> filterMovies(List<Movie> movies, String search) {
        if (!search.isEmpty()) {
            return movies.stream()
                    .filter(movie -> movie.getTitle().toLowerCase().contains(search.toLowerCase()) ||
                            movie.getActors().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return movies;
    }

    // Sort movies based on the sort criteria
    private List<Movie> sortMovies(List<Movie> movies, String sort) {
        switch (sort) {
            case "title":
                movies.sort(Comparator.comparing(Movie::getTitle));
                break;
            case "actors":
                movies.sort(Comparator.comparing(Movie::getActors));
                break;
            case "year":
                movies.sort(Comparator.comparingInt(Movie::getYear));
                break;
        }
        return movies;
    }
}
