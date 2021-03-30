package com.training.springbootsecurity.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final JwtConfig jwtConfig;
  private final SecretKey secretKey;


  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response)
          throws AuthenticationException {
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
                                          Authentication authResult) throws IOException {

    String token = Jwts.builder()
            .setSubject(authResult.getName())
            .claim("authorities", authResult.getAuthorities())
            .setIssuedAt(new Date())
            .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getExpirationDays())))
            .signWith(secretKey)
            .compact();


    Map<String, String> responseToken = new HashMap<>();
    responseToken.put("token", String.format("%s %s", jwtConfig.getTokenPrefix(), token));

    response.setContentType("application/json");

    response.addHeader(
            jwtConfig.getAuthorizationHeader(),
            String.format("%s %s", jwtConfig.getTokenPrefix(), token)
    );
    response.getWriter().write(String.valueOf(responseToken));
  }

}
