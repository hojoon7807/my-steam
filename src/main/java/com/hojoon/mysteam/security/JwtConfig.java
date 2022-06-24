package com.hojoon.mysteam.security;

import com.hojoon.mysteam.security.jwt.JwtAuthenticationFilter;
import com.hojoon.mysteam.security.jwt.JwtAuthorizationFilter;
import com.hojoon.mysteam.security.jwt.JwtProvider;
import com.hojoon.mysteam.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class JwtConfig extends AbstractHttpConfigurer<JwtConfig, HttpSecurity> {

  private final JwtProvider jwtProvider;
  private final JwtAuthorizationFilter jwtAuthorizationFilter;
  private final CookieUtil cookieUtil;

  @Override
  public void configure(
      HttpSecurity http) throws Exception {
    AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
    http.addFilter(usernamePasswordAuthenticationFilter(authenticationManager))
        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(
      AuthenticationManager authenticationManager) {
    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
        authenticationManager, jwtProvider, cookieUtil);
    jwtAuthenticationFilter.setFilterProcessesUrl("/signin");
    return jwtAuthenticationFilter;
  }
}
