package edu.ap.spring.examen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class ExamenApplication {

    /*
     * Zie redis/InitDb voor uitleg in geval van een error (Unable to connect to Redis)!
     */
    public static void main(String[] args) {
        SpringApplication.run(ExamenApplication.class, args);
    }
}
