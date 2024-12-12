package com.ProiektuaCochea.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ProiektuaCochea.app.domain.erabiltzailea;
import com.ProiektuaCochea.app.repository.ErabiltzaileaRepository;

@Configuration
public class SecurityConfig {

    @Autowired
    private ErabiltzaileaRepository erabiltzaileaRepository;
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/", "/login", "/register", "/css/**", "/js/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(login -> login
                                .loginPage("/login")
                                .defaultSuccessUrl("/ikusi-kotxeak", true)
                                .permitAll()
                )
                .logout(logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                                .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        erabiltzailea user = erabiltzaileaRepository.findByNombre(username);

        if (user == null) {
            throw new UsernameNotFoundException("Erabiltzailea ez da aurkitu");
        }

      
        System.out.println("Usuario: " + user.getNombre() + ", Rol: " + user.getRol());

        String role = user.getRol() ? "ADMIN" : "USER";

        return User.builder()
                .username(user.getNombre())
                .password(user.getPassword())
                .roles(role) 
                .build();
    }

}


