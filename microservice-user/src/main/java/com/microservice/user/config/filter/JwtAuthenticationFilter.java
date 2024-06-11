package com.microservice.user.config.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = extractJwtFromRequest(request);
            if (jwt != null && Jwts.parser().setSigningKey(SECRET).isSigned(jwt)) {
                // Token válido, permitir que la solicitud continúe
                filterChain.doFilter(request, response);
            } else {
                // Token inválido o ausente, devolver un estado de no autorizado
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        } catch (ExpiredJwtException e) {
            // Manejo de token expirado si es necesario
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    private String extractJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
