package org.eusebiu.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component // Spunem lui Spring Boot sa o tina in memorie ca pe o unealta
public class JwtUtil {

    // 1. Cheia noastra secreta (luata din application.properties)
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // 2. Cat timp e valabila bratara (aici am pus 24 de ore in milisecunde)
    private final long EXPIRATION_TIME = 86400000;

    // Metoda interna care pregateste cheia pentru semnatura
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 🌟 FABRICA: Metoda care genereaza token-ul
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Aici spunem A CUI este bratara (punem emailul)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Cand a fost printata
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Cand expira
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // O semnam cu cheia noastra secreta (ca sa nu poata fi falsificata)
                .compact(); // O impachetam intr-un String lung (Token-ul)
    }

    // 🌟 CITITORUL: Extrage email-ul din token
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 🌟 VALIDATORUL: Verifica daca cineva a modificat token-ul sau a expirat
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true; // Daca trece de parsare fara eroare, token-ul e 100% valid!
        } catch (Exception e) {
            return false; // Daca cineva a umblat la el, sau a expirat, arunca eroare si returnam false
        }
    }
}