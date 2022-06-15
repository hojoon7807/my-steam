package com.hojoon.mysteam.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtEntryPoint jwtEntryPoint;
  private final JwtAccessDeniedHanler jwtAccessDeniedHanler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .httpBasic().disable()
        .cors()

        .and()
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(jwtEntryPoint)
        .accessDeniedHandler(jwtAccessDeniedHanler)

        .and()
        .authorizeRequests()
        .antMatchers("/", "/signup").permitAll()
        .anyRequest().authenticated()

        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    return http.build();
  }
}
