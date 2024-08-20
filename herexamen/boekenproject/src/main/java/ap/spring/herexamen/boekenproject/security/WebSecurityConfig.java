package ap.spring.herexamen.boekenproject.security;

import ap.spring.herexamen.boekenproject.jpa.WebUser;
import ap.spring.herexamen.boekenproject.jpa.WebUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private WebUserRepository repo;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login", "/h2-console/**").permitAll()
                        //.requestMatchers("/home").hasRole("WEBUSER") ------------------------ uncomment dit voor werkende authenticatie
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll())
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

        return new InMemoryUserDetailsManager(reloadUsers(repo));
    }

    public static ArrayList<UserDetails> reloadUsers(WebUserRepository repo) {
        ArrayList<UserDetails> userDetails = new ArrayList<UserDetails>();
        Iterable<WebUser> users = repo.findAll();
        for (WebUser u : users) {
            UserDetails user = User.builder()
                    .username(u.getUsername())
                    .password(u.getPassword())
                    .build();
            userDetails.add(user);
        }
        return userDetails;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
