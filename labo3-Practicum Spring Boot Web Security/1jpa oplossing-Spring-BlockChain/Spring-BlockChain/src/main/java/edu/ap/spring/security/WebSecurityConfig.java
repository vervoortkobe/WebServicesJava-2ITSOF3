package edu.ap.spring.security;

import edu.ap.spring.jpa.Roles;
import edu.ap.spring.jpa.UserRepository;
import edu.ap.spring.jpa.WebUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // secret123
    private static final String ENCODED_PASSWORD = "$2a$10$AIUufK8g6EFhBcumRRV2L.AQNz3Bjp7oDQVFiO5JJMBFZQ6x2/R/2";

    @Autowired
    UserRepository repo;

    @PostConstruct
    public void postConstruct() {

        WebUser user = new WebUser("user", ENCODED_PASSWORD, Roles.USER.name());
        repo.save(user);
        WebUser admin = new WebUser("admin", ENCODED_PASSWORD, Roles.ADMIN.name());
        repo.save(admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home", "/balance/{wallet}", "/transaction", "/valid").permitAll()
                        .requestMatchers("/alltransactions").hasRole(Roles.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

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