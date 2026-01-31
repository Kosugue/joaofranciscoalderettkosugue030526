package com.seplagseletivo.projeto_backend;

import com.seplagseletivo.projeto_backend.security.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Injeta os valores do application.properties na classe
        ReflectionTestUtils.setField(jwtService, "secret", secretKey);
        ReflectionTestUtils.setField(jwtService, "expiration", 300000L); // 5 minutos
    }

    @Test
    void shouldGenerateValidToken() {
        UserDetails user = new User("admin@test.com", "pass", new ArrayList<>());
        String token = jwtService.generateToken(user.getUsername());

        assertNotNull(token);
        assertEquals("admin@test.com", jwtService.extractUsername(token));
    }

    @Test
    void shouldValidateTokenCorrectly() {
        UserDetails user = new User("admin@test.com", "pass", new ArrayList<>());
        String token = jwtService.generateToken(user.getUsername());

        assertTrue(jwtService.isTokenValid(token, user));
    }

    @Test
    void shouldDetectExpiredToken() {
        // Gera um token manualmente com data no passado
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        String expiredToken = Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject("user")
                .setIssuedAt(new Date(System.currentTimeMillis() - 10000)) // 10s atrás
                .setExpiration(new Date(System.currentTimeMillis() - 5000)) // Expirou há 5s
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> {
            jwtService.isTokenValid(expiredToken, new User("user", "", new ArrayList<>()));
        });
    }
}