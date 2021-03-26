package com.training.springbootsecurity.auth;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.training.springbootsecurity.security.ApplicationUserRole.ADMIN;
import static com.training.springbootsecurity.security.ApplicationUserRole.USER;

@Repository
@AllArgsConstructor
public class ApplicationUserRepositoryImpl implements ApplicationUserRepository {

  private final PasswordEncoder passwordEncoder;

  @Override
  public Optional<ApplicationUser> getApplicationUserByUsername(String username) {
    return getApplicationUsers()
            .stream()
            .filter(applicationUser -> username.equals(applicationUser.getUsername()))
            .findFirst();
  }

  private List<ApplicationUser> getApplicationUsers() {
    return Lists.newArrayList(
            new ApplicationUser(
                    "annasmith",
                    passwordEncoder.encode("password"),
                    USER.getGrantedAuthorities(),
                    true,
                    true,
                    true,
                    true
            ),
            new ApplicationUser(
                    "linda",
                    passwordEncoder.encode("password"),
                    ADMIN.getGrantedAuthorities(),
                    true,
                    true,
                    true,
                    true
            ),
            new ApplicationUser(
                    "tom",
                    passwordEncoder.encode("password"),
                    USER.getGrantedAuthorities(),
                    true,
                    true,
                    true,
                    true
            )
    );
  }
}
