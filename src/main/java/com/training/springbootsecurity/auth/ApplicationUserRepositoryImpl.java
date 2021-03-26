package com.training.springbootsecurity.auth;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ApplicationUserRepositoryImpl implements ApplicationUserRepository {

  private final PasswordEncoder passwordEncoder;

  @Override
  public Optional<ApplicationUser> getApplicationUserByUsername(String username) {
    return Optional.empty();
  }

//  private List<ApplicationUser> getApplicationUsers(){
//    List<ApplicationUser> applicationUsers = Lists.newArrayList(
//            new ApplicationUser(
//                    "annasmith",
//                    passwordEncoder.encode("password"),
//                    STUDENT.
//
//            )
//
//    );
//  }
}
