package com.trabalho.kanban.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class configure {

    @Value("${jwt.secret}")
    private String secretKey; // Agora a chave secreta vem do arquivo de propriedades

    // Gera o token com o nome de usuário
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5)) // 5 horas de validade
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Valida se o token é válido
    public boolean validateToken(String token, String username) {
        if (token == null || username == null) {
            return false; // Retorna false se o token ou o username forem nulos
        }

        String extractedUsername = extractUsername(token);
        return username.equals(extractedUsername) && !isTokenExpired(token);
    }

    // Extrai os dados do token
    private Claims extractClaims(String token) {
        if (token == null) {
            return null; // Retorna null se o token for nulo
        }

        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null; // Retorna null em caso de erro ao processar o token
        }
    }

    // Extrai o nome de usuário do token
    public String extractUsername(String token) {
        if (token == null) {
            return null; // Retorna null se o token for nulo
        }

        Claims claims = extractClaims(token);
        return claims != null ? claims.getSubject() : null; // Retorna o username ou null se não conseguir extrair
    }

    // Verifica se o token expirou
    public boolean isTokenExpired(String token) {
        if (token == null) {
            return true; // Considera que o token está expirado se for nulo
        }

        Claims claims = extractClaims(token);
        return claims != null && claims.getExpiration().before(new Date()); // Verifica a expiração
    }
}
