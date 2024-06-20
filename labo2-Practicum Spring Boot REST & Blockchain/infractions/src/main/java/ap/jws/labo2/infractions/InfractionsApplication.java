package ap.jws.labo2.infractions;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;

import ap.jws.labo2.infractions.jpa.Infraction;
import ap.jws.labo2.infractions.jpa.InfractionRepository;

@SpringBootApplication
public class InfractionsApplication implements CommandLineRunner {
	
   @Autowired
   private InfractionRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(InfractionsApplication.class, args);
	}

	@Override
    public void run(String... args) throws IOException {

			ObjectMapper objectMapper = new ObjectMapper();

			Infraction[] infractions = objectMapper.readValue(new File("src/main/resources/infractions.json"), Infraction[].class);

			repository.saveAll(Arrays.asList(infractions));
		}
	}
