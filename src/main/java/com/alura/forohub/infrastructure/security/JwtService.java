package com.alura.forohub.infrastructure.security;

import com.alura.forohub.domain.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${api.jwt.secret}")
    private String secret;

    @Value("${api.jwt.expiration}")
    private long expiration;

    public String generateToken(User user) {
        Instant now = Instant.now();
        Instant expirationInstant = now.plusMillis(expiration);
        Date expirationDate = Date.from(expirationInstant);
        
        // Logging temporal para diagnóstico
        logger.debug("Generando token JWT - Expiration (ms): {}, Expiration Date: {}", 
            expiration, expirationDate);
        
        return JWT.create()
            .withSubject(user.getEmail())
            .withClaim("name", user.getName())
            .withIssuedAt(Date.from(now))
            .withExpiresAt(expirationDate)
            .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndGetSubject(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
