package edu.ap.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import edu.ap.spring.redis.RedisService;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class RedisApplication {
	
	@Autowired
	private RedisService service;

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}
		
	@PostConstruct
	public void init() {
			// empty db
			this.service.flushDb();

			// movies
			this.service.setKey("moviescount", "0");
			this.service.rpush("movies:1998:The Big Lebowski", "Jeff Bridges");
			this.service.rpush("movies:1998:The Big Lebowski", "John Goodman");
			this.service.rpush("movies:1998:The Big Lebowski", "John Turturro");
			this.service.rpush("movies:1998:The Big Lebowski", "Steve Buscemi");
			this.service.incr("moviescount");
			
			System.out.println("Total movies : " + service.getKey("moviescount"));
	}
}
