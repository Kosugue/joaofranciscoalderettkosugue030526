package com.seplagseletivo.projeto_backend.service;

import com.seplagseletivo.projeto_backend.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;
    // O AuthenticationManager é o motor do Spring Security que valida as credenciais
    private final AuthenticationManager authenticationManager;

    public AuthService(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String login(String username, String password) {
        // 1. Tenta autenticar o usuário com as credenciais fornecidas
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // 2. Se a autenticação falhar, o Spring joga uma exceção automaticamente.
        // Se passar, geramos o token JWT (que tem os 5 minutos definidos no JwtService)
        return jwtService.generateToken(username);
    }
}