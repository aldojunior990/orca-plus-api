package com.aldoj.orca_plus_api.configs;

import com.aldoj.orca_plus_api.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    public String secret; // KEY para gerar tokens unicos nesta aplicação

    // Função para gerar tokens JWT para a aplicação
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret); // Algoritimo para gerar o JWT
            return JWT.create().withIssuer("orca_plus").withSubject(user.getEmail()).withExpiresAt(generateExpirationDate()).sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token.", e);
        }
    }

    // Função para validar token
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).withIssuer("orca_plus").build().verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    // Tempo de expiração de 2 horas
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
