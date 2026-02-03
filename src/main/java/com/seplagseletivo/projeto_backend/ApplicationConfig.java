package com.seplagseletivo.projeto_backend;

import com.seplagseletivo.projeto_backend.repository.UserRepository;

import com.seplagseletivo.projeto_backend.model.User;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    private final UserRepository userRepository;

    public ApplicationConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Seu código busca por email, então o login deve ser o email
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // --- correção login  ---

    @Bean
    public CommandLineRunner createAdminUser(PasswordEncoder encoder) {
        return args -> {
            String emailAdmin = "admin@test.com";

            // Verifica
            if (userRepository.findByEmail(emailAdmin).isEmpty()) {
                User admin = new User();

                // Ajuste
                admin.setName("Administrador"); // ou setFirstname, etc.
                admin.setEmail(emailAdmin);

                // Criptografar a senha antes de salvar
                admin.setPassword(encoder.encode("password123"));

                userRepository.save(admin);
                System.out.println("✅ USUÁRIO DE TESTE CRIADO: " + emailAdmin + " / password123");
            }
        };
    }
}