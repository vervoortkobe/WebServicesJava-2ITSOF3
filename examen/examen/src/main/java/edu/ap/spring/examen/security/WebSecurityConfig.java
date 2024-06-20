package edu.ap.spring.examen.security;

import java.util.ArrayList;

import edu.ap.spring.examen.repository.UserRepository;
import edu.ap.spring.examen.repository.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


/*
 * 1/ Beveilig de applicatie met Spring Web Security. Maak één enkele user ('admin', '@dmin') aan bij het opstarten die kan inloggen (3 punten).
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserRepository repo;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    public void addUser(WebUser user) {
        UserDetails newUser = User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .build();

        inMemoryUserDetailsManager().createUser(newUser);
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        ArrayList<UserDetails> userDetails = new ArrayList<UserDetails>();
        Iterable<WebUser> users = repo.findAll();
        for(WebUser u : users) {
            UserDetails user = User.builder()
                    .username(u.getUserName())
                    .password(u.getPassword())
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
