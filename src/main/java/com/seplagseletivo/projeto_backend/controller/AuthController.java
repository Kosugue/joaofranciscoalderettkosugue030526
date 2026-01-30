package com.seplagseletivo.projeto_backend.controller;

import com.seplagseletivo.projeto_backend.dto.AuthRequestDTO;
import com.seplagseletivo.projeto_backend.dto.AuthResponseDTO;
import com.seplagseletivo.projeto_backend.security.JwtService;
import com.seplagseletivo.projeto_backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticação")
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authService;

    public AuthController(JwtService jwtService, AuthService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login (5 min exp)", description = "Atende Requisito B")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        try {
            // Validação via AuthService (que usa o AuthenticationManager)
            String token = authService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new AuthResponseDTO(token));
        } catch (Exception e) {
            // Retorna 401 caso as credenciais (admin/password123) estejam incorretas
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/refresh")
    @Operation(summary = "Renovação de Token", description = "Atende Requisito B")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        // Validação simplificada: se o username existe no token e ele não expirou
        if (username != null && !jwtService.isTokenExpired(token)) {
            String newToken = jwtService.generateToken(username);
            return ResponseEntity.ok(new AuthResponseDTO(newToken));
        }

        return ResponseEntity.status(401).build();
    }
}