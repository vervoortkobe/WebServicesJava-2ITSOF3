package edu.ap.spring.websecurity.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import edu.ap.spring.websecurity.jpa.*;
import jakarta.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	// secret123
	private static final String ENCODED_PASSWORD = "$2a$10$AIUufK8g6EFhBcumRRV2L.AQNz3Bjp7oDQVFiO5JJMBFZQ6x2/R/2";

	@Autowired
	UserRepository repo;

	@PostConstruct
	public void postConstruct() {

		WebUser user = new WebUser("user", ENCODED_PASSWORD, "USER");
		repo.save(user);
		WebUser admin = new WebUser("admin", ENCODED_PASSWORD, "ADMIN");
		repo.save(admin);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/", "/home").permitAll()
				.requestMatchers("/admin").hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
	}

	@Bean
    public UserDetailsService users() {

		/*UserDetails user = User.builder()
                .username("user")
                .password(ENCODED_PASSWORD)
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(ENCODED_PASSWORD)
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);*/

		ArrayList<UserDetails> userDetails = new ArrayList<UserDetails>();
		Iterable<WebUser> users = repo.findAll();
		for(WebUser u : users) {
			UserDetails user = User.builder()
                .username(u.getUserName())
                .password(u.getPassword())
                .roles(u.getRole())
                .build();
			userDetails.add(user);
		}

		return new InMemoryUserDetailsManager(userDetails);
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
	}
}
