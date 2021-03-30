package com.training.springbootsecurity.jwt;

import com.google.common.net.HttpHeaders;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class JwtConfig {

  @Value("${jwt.secret-key}")
  private String secretKey;

  @Value("${jwt.token-prefix}")
  private String tokenPrefix;

  @Value("${jwt.expiration-days}")
  private Integer expirationDays;

  public JwtConfig() {
  }

  public String getAuthorizationHeader() {
    return HttpHeaders.AUTHORIZATION;
  }

}
