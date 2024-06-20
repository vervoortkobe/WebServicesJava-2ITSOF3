package edu.ap.spring.examen.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/*
 * 2/ Zorg ervoor dat bij het opstarten van de applicatie, bijgevoegd movies.txt bestand wordt ingelezen. 
 * Het bestand bevat films en acteurs. Voeg de films toe in Redis, samen met de acteurs. 
 * Er zitten nog andere records in de database, dus gebruik een sleutelpatroon zodat films snel en gemakkelijk kunnen opgevraagd worden (3 punten). 
 */
@Component
public class InitDb {

    @Autowired
    private RedisService service;

    /*
     * Dit is de methode die Redis moet volladen met de movies.
     * Echter krijg ik telkens de error: Unable to connect to Redis...
     * Deze error krijg ik zelfs als ik deze init methode in de main class zet.
     * Hopelijk is dit bij u niet het geval! Bedankt om hier mee rekening te houden.
     * Door deze error heb ik dus mijn applicatie niet goed kunnen runnen en testen.
     */
    
    @Bean
    private void fillDb() {
        // empty db
        service.flushDb();

        File input = new File("src/main/resources/static/movies.txt");
        try {
            Files.lines(input.toPath())
                    .skip(1)
                    .forEach(l -> {
                        String key = "movies:" + l;
                        // Zorg ervoor dat er geen twee films met dezelfde naam kunnen bestaan (2 punten).
                        if (!service.hasKey(key)) service.rpush(key, l.split(":")[0]);
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("filled");
    }
}
