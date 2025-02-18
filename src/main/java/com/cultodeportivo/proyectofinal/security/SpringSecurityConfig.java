package com.cultodeportivo.proyectofinal.security;

import com.cultodeportivo.proyectofinal.security.filter.JwtAuthenticationFilter;
import com.cultodeportivo.proyectofinal.security.filter.JwtValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // Habilita @PreAuthorize en los controladores
public class SpringSecurityConfig {

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configurar CORS
                .authorizeHttpRequests(authz -> authz
                        // Permitir acceso sin autenticación solo a login y registro
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()

                        // Acceso a rutas por roles
                        .requestMatchers("/api/usuarios/**").permitAll()
                        .requestMatchers("/api/cajeros/**").permitAll()
                        .requestMatchers("/api/tarifas/**").permitAll()
                        .requestMatchers("/api/espacios/**").permitAll()
                        .requestMatchers("/api/contratos/**").permitAll()
                        .requestMatchers("/api/usuarios/**").permitAll()
                        .requestMatchers("/api/cajeros/**").permitAll()
                        .requestMatchers("/api/tickets/**").permitAll()
                        .requestMatchers("/api/horarios/**").permitAll()
                        .requestMatchers("/api/personas/**").permitAll()
                        .requestMatchers("/api/roles/**").permitAll()
                        .requestMatchers("/api/vehiculos/**").permitAll()


                        // Acceso protegido para otras rutas
                        .anyRequest().authenticated()
                )
                .addFilter(new JwtAuthenticationFilter(authenticationManager))
                .addFilter(new JwtValidationFilter(authenticationManager))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
