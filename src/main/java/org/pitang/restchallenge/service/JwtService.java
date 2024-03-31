package org.pitang.restchallenge.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class JwtService {
	
	@Value("${pitang.jwt.secret}")
	private String secretKey;
	
	record JwtResponse(String token) {}
	
    public JwtResponse generateJwtToken(String username) {
    	Algorithm key = Algorithm.HMAC256(secretKey);

        String jwt = JWT.create()
                .withIssuer("auth-api")
                .withSubject(username)
                .withExpiresAt(genExpirationDate())
                .sign(key);

        return new JwtResponse(jwt);
    }
    
    public String validateToken(String token) throws ExpiredJwtException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
