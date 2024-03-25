package org.pitang.restchallenge;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.crypto.SecretKey;

import org.pitang.restchallenge.dto.ErrorMessagesDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String secretKey = "secretKeysecretKeysecretKeysecretKey";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer "
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

            try {
                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token);

                String username = claimsJws.getBody().getSubject();

                if (username != null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            username, null, new ArrayList<>()); // Autoridades podem ser carregadas com base nos claims do token

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
                
            } catch (ExpiredJwtException e) {
            	ErrorMessagesDto errorToken = new ErrorMessagesDto("Unauthorized - invalid session", HttpStatus.FORBIDDEN.value());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Ou outro status apropriado
                response.setContentType("application/json");
                
                ObjectMapper mapper = new ObjectMapper();
                String errorResponse = mapper.writeValueAsString(errorToken);
                
                response.getWriter().write(errorResponse);
                return;
            } catch (Exception e) {
            	ErrorMessagesDto errorToken = new ErrorMessagesDto("Unauthorized", HttpStatus.FORBIDDEN.value());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Ou outro status apropriado
                response.setContentType("application/json");
                
                ObjectMapper mapper = new ObjectMapper();
                String errorResponse = mapper.writeValueAsString(errorToken);
                
                response.getWriter().write(errorResponse);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}

