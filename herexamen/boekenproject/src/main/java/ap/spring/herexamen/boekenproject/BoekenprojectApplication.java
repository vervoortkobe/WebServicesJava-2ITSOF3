package ap.spring.herexamen.boekenproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoekenprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoekenprojectApplication.class, args);
	}

	/*
	Beste,

	1. AUTHENTICATIE
	De authenticatie van mijn applicatie werkt niet volledig.
	Er wordt niet juist gevalideerd op gebruikers die willen inloggen.
	Daarom heb ik in WebSecurityConfig de lijn met .roles in comment gezet, zodat je gemakkelijk aan de /home kunt, zonder dat dit een 403 teruggeeft.
	Hierdoor werkt de authenticatie verder wel qua afweren van niet-ingelogde gebruikers.
	2. REDIRECT LOGIN
	Na het inloggen, volgt er geen redirect, toch staat dit juist in mijn code!

	Alvast bedankt om hier rekening mee te houden.
	Mvg,
	Kobe Vervoort   2ITSOF3
	 */

}
