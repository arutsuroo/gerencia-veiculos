package com.example.gerencia_veiculos.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/h2-console/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/veiculos/**").hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/veiculos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/veiculos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/veiculos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/veiculos/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acesso negado: Token ausente ou expirado.");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                            if (auth instanceof AnonymousAuthenticationToken || auth == null) {
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Autenticação necessária.");
                            } else {
                                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Perfil insuficiente para realizar esta operação.");
                            }
                        })
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}