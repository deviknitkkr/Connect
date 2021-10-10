package com.vikas.connect.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    private Algorithm getJwtAlgorithm() {
        return Algorithm.HMAC256(jwtSecret);
    }

    public String generateToken(String username) {

        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 10000000))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .sign(getJwtAlgorithm());
    }

    public String getUsernameFromJWT(String token) {
        return JWT.decode(token).getSubject();
    }

    public String validateToken(String authToken) {

        try {
            JWTVerifier jwtVerifier = JWT.require(getJwtAlgorithm()).build();
            jwtVerifier.verify(authToken);
            return "valid";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}