package com.training.springbootsecurity.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response) throws AuthenticationException {
    try {
      AuthenticationRequest authenticationRequest = new ObjectMapper()
              .readValue(request.getInputStream(), AuthenticationRequest.class);
      Authentication authentication = new UsernamePasswordAuthenticationToken(
              authenticationRequest.getUsername(),
              authenticationRequest.getPassword()
      );
      return authenticationManager.authenticate(authentication);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) {

    String secretKey = "q3t6w9y$B&E)H@McQfTjWnZr4u7x!A%C*F-JaNdRgUkXp2s5v8y/B?E(G+KbPeSh";

    String token = Jwts.builder()
            .setSubject(authResult.getName())
            .claim("authorities", authResult.getAuthorities())
            .setIssuedAt(new Date())
            .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .compact();

    response.addHeader(
            "Authorization",
            String.format("Bearer %s", token)
    );
  }

}
