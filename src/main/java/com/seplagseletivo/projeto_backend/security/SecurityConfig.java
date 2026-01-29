package com.seplagseletivo.projeto_backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF (necessário para APIs Stateless)
                .csrf(csrf -> csrf.disable())

                // Permite frames (NECESSÁRIO para o H2 Console funcionar)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                .authorizeHttpRequests(auth -> auth
                        // --- Rotas Públicas (Sem Login) ---
                        // 1. Banco de Dados H2
                        .requestMatchers("/h2-console/**").permitAll()

                        // 2. Documentação Swagger/OpenAPI
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // 3. Autenticação (Login/Registro)
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // 4. Actuator (Health Checks - Requisito A)
                        .requestMatchers("/actuator/**").permitAll()

                        // 5. WebSocket (Requisito C)
                        .requestMatchers("/ws-albums/**").permitAll()

                        // --- Todas as outras rotas exigem Token JWT ---
                        .anyRequest().authenticated()
                )

                // Define que não guardaremos sessão no servidor (Stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Adiciona nosso filtro de JWT antes do filtro padrão do Spring
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}