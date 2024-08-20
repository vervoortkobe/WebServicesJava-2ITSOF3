package oef1.fullproject.security;

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

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(requests -> requests
            .requestMatchers("/login", "/h2-console/**").permitAll() // Allow access to these paths
            .requestMatchers("/home").hasRole("ADMIN") // Require USER role for /home
            .anyRequest().authenticated() // All other requests require authentication
        )
        .formLogin(form -> form
            .loginPage("/login")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout") // Specify the logout URL
            .logoutSuccessUrl("/login") // Redirect after logout
            .permitAll()
        );
    
        return http.build();
    }
    
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        List<UserDetails> u = new ArrayList<>();
        UserDetails user = User.builder()
        .username("admin")
        .password(passwordEncoder().encode("admin"))
        .roles("ADMIN")
        .build();
        UserDetails user2 = User.builder()
        .username("user")
        .password(passwordEncoder().encode("user"))
        .roles("USER")
        .build();
        u.add(user);
        u.add(user2);

        return new InMemoryUserDetailsManager(u);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
