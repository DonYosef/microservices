package com.microservice.product.config.filter;

import com.microservice.product.error.NoAuthException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    private static final List<Pattern> EXCLUDED_URL_PATTERNS = List.of(
            Pattern.compile("/api/product/all"),
            Pattern.compile("/api/product/search/\\d+"), // \d+ for numerical ids
            Pattern.compile("/api/product/search-by-user/\\d+")
    );
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            String requestPath = request.getServletPath();
            if (isExcluded(requestPath)) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwt = extractJwtFromRequest(request);
            if (jwt == null || !Jwts.parser().setSigningKey(SECRET).isSigned(jwt)) {
                throw new NoAuthException("Acceso denegado");
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Token expirado");
        } catch (NoAuthException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Acceso denegado");
        }
    }

    private boolean isExcluded(String requestPath) {
        return EXCLUDED_URL_PATTERNS.stream().anyMatch(pattern -> pattern.matcher(requestPath).matches());
    }

    private String extractJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
