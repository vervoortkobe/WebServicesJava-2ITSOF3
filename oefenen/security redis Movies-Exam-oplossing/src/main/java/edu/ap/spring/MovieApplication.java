package edu.ap.spring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

import edu.ap.spring.redis.RedisService;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class MovieApplication {

	@Autowired
	private RedisService service;
	@Value("classpath:movies.txt")
	Resource resource;

	@PostConstruct
	public void init() {

		InputStream inputStream;
		try {
			inputStream = resource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(":");
				String movieName = parts[0];
				String[] parts2 = parts[1].split("-");
				String actorPart = parts2[0];
				String year = parts2[1];
				String[] actors = actorPart.split(",");
				String key = "movies:" + year + ":" + movieName;
				if (!this.service.hasKey(key)) {
					for (String actor : actors) {
						this.service.rpush(key, actor);
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieApplication.class, args);
	}
}
