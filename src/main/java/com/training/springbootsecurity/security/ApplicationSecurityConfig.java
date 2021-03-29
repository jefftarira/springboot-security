package com.training.springbootsecurity.security;

import com.training.springbootsecurity.auth.ApplicationUserService;
import com.training.springbootsecurity.jwt.JwtAuthenticationFilter;
import com.training.springbootsecurity.jwt.JwtTokenVerifier;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.training.springbootsecurity.security.ApplicationUserRole.USER;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final ApplicationUserService applicationUserService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(new JwtAuthenticationFilter(authenticationManager()))
            .addFilterAfter(new JwtTokenVerifier(), JwtAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
            .antMatchers("/api/**").hasRole(USER.name())
            .anyRequest()
            .authenticated();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder);
    provider.setUserDetailsService(applicationUserService);
    return provider;
  }

}
