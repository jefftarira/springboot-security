package com.training.springbootsecurity.auth;

import java.util.Optional;

public interface ApplicationUserRepository {
  Optional<ApplicationUser> getApplicationUserByUsername(String username);
}
