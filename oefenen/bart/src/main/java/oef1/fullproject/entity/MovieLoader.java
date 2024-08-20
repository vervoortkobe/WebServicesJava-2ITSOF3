package oef1.fullproject.entity;

import oef1.fullproject.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Component
public class MovieLoader implements CommandLineRunner {

    @Autowired
    private RedisService redisService;

    @Override
    public void run(String... args) throws Exception {
        deleteKeysMatchingPattern("movie:*");
        loadMoviesFromFile();
    }
    public void deleteKeysMatchingPattern(String pattern) {
        // Retrieve all keys matching the pattern
        Set<String> keys = redisService.keys(pattern);

        // Iterate through the keys and delete each one
        for (String key : keys) {
            redisService.delete(key);
            System.out.println("Deleted key: " + key);
        }

        System.out.println("All matching keys deleted.");
    }

    public void loadMoviesFromFile() {
        Resource resource = new ClassPathResource("static/movies.txt");
        
        // Debug log to confirm resource path
        System.out.println("Resource path: " + resource.toString());
    
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Debug log to confirm content is read
                System.out.println("Reading line: " + line);
    
                // Split the line by colon to separate title and rest
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String title = parts[0].trim();
                    
                    // Split the rest by hyphen to separate actors and year
                    String[] rest = parts[1].split("-", 2);
                    if (rest.length == 2) {
                        String actors = rest[0].trim();
                        int year;
                        try {
                            year = Integer.parseInt(rest[1].trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Skipping line due to invalid year format: " + rest[1].trim());
                            continue;
                        }
                        
                        Movie movie = new Movie(title, actors, year);
                        Map<String, String> movieMap = new HashMap<>();
                        movieMap.put("title", movie.getTitle());
                        movieMap.put("actors", movie.getActors());
                        movieMap.put("year", String.valueOf(movie.getYear()));
                        //IN TO REDIS
                        redisService.hset("movie:" + movie.getTitle().replaceAll(" ", "_"), movieMap);
    
                        // Log the movie
                        System.out.println("Loaded movie: " + movie);
                    } else {
                        System.out.println("Skipping invalid line due to incorrect format: " + line);
                    }
                } else {
                    System.out.println("Skipping invalid line due to incorrect format: " + line);
                }
            }
            System.out.println("LOAD DATA completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
