package com.hojoon.mysteam.security;

import com.hojoon.mysteam.security.jwt.JwtAccessDeniedHanler;
import com.hojoon.mysteam.security.jwt.JwtEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtEntryPoint jwtEntryPoint;
  private final JwtAccessDeniedHanler jwtAccessDeniedHanler;
  private final JwtConfig jwtConfig;

  @Qualifier("memberDetailsService")
  private final UserDetailsService userDetailsService;

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
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
        .authorizeRequests()
        .antMatchers("/", "/signup", "/signin", "/login").permitAll()
        .anyRequest().authenticated()

        .and()
        .apply(jwtConfig);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

}
