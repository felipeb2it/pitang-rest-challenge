package org.pitang.restchallenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
    JwtAuthenticationFilter securityFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf((csrf) -> csrf.disable())
        	.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/signin", "/api/users/**", "/error", "/swagger-ui/**", "/v3/**").permitAll()
                .anyRequest().authenticated()
            )
            .build();
    }
}
