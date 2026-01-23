package com.church.appChurch.infra.security;

import com.church.appChurch.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;


@Service
public class TokenService {

    @Value("${api.security.token.secret:MySecretKey123456789012345678901234567890}")
    private String secret;

    public String generateToken(Usuario usuario) {
        try {
            return Jwts.builder()
                    .setSubject(usuario.getUsername())
                    .setIssuer("auth-api")
                    .setExpiration(Date.from(genExpirationDate()))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token", e);
        }

    }

    public String validateToken(String token) {

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }catch (Exception e){
            return "";
        }

    }

    private Key getSignInKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secret);
        byte[] keyBytes = secret.getBytes(); // <--- USE ISTO
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
