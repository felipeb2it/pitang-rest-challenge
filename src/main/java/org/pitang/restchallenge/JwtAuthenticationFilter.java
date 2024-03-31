package org.pitang.restchallenge;

import java.io.IOException;

import org.pitang.restchallenge.dto.ErrorMessagesDto;
import org.pitang.restchallenge.service.JwtService;
import org.pitang.restchallenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer "
            // SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

            try {
            	var login = jwtService.validateToken(token);
            	var userDetails = userService.loadUserByUsername(login);
            	
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
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

