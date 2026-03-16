package org.eusebiu.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Ne uitam in cerere dupa antetul "Authorization"
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // 2. Frontend-ul trimite bratarile in formatul: "Bearer ewab312..."
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Taiem cuvantul "Bearer " ca sa ramana doar codul
            try {
                email = jwtUtil.extractEmail(token); // Extragem email-ul
            } catch (Exception e) {
                System.out.println("Eroare la citirea token-ului: " + e.getMessage());
            }
        }

        // 3. Daca am gasit un email si omul nu a fost deja validat de Spring Boot
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Scanner-ul verifica: e valabila bratara? Nu e expirata? E semnata de noi?
            if (jwtUtil.isTokenValid(token)) {

                // DACA DA: Il declaram oficial LOGAT pentru aceasta cerere!
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 4. Deschidem usa si lasam cererea sa mearga mai departe spre Controller
        filterChain.doFilter(request, response);
    }
}
