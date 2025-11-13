package com.help_your_partner.task_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Desactiva CSRF porque usamos JWT (stateless)
            .csrf(csrf -> csrf.disable())

            // 2. Establece la política de sesión como STATELESS
            // Spring Security no creará ni usará sesiones HTTP.
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 3. Configura las reglas de autorización
            .authorizeHttpRequests(authz -> authz                
                // 4. Requiere autenticación para cualquier petición
                .anyRequest().authenticated()
            );


        return http.build();
    }

}
