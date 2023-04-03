package com.howtodoinjava.app.auth;

import io.dropwizard.auth.Authorizer;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.checkerframework.checker.nullness.qual.Nullable;

public class AppAuthorizer implements Authorizer<User> {

  @Override
  public boolean authorize(User user, String role,
      @Nullable ContainerRequestContext containerRequestContext) {
    return user.getRoles() != null && user.getRoles().contains(role);
  }
}
