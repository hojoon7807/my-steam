package com.hojoon.mysteam.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hojoon.mysteam.member.adapter.rest.dto.request.SignInMemberRequest;
import com.hojoon.mysteam.util.CookieUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtProvider jwtProvider;
  private final CookieUtil cookieUtil;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
      JwtProvider jwtProvider, CookieUtil cookieUtil) {
    super(authenticationManager);
    this.jwtProvider = jwtProvider;
    this.cookieUtil = cookieUtil;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

    SignInMemberRequest signInMember = null;
    try {
      signInMember = objectMapper.readValue(request.getInputStream(), SignInMemberRequest.class);
      log.info("User Email is: {}", signInMember.getEmail());
    } catch (IOException e) {
      // 임시 예외 throw customException 필요
      throw new RuntimeException(e.getMessage());
    }
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        signInMember.getEmail(), signInMember.getPassword());

    return getAuthenticationManager().authenticate(authenticationToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    Cookie accessToken = cookieUtil.createCookie(
        JwtExpiration.ACCESS_TOKEN_EXPIRATION_TIME.getTokenType(),
        jwtProvider.generateAccessToken(authResult),
        JwtExpiration.ACCESS_TOKEN_EXPIRATION_TIME.getExpiration());

    Cookie refreshToken = cookieUtil.createCookie(
        JwtExpiration.REFRESH_TOKEN_EXPIRATION_TIME.getTokenType(),
        jwtProvider.generateRefreshToken(authResult),
        JwtExpiration.REFRESH_TOKEN_EXPIRATION_TIME.getExpiration());
    response.addCookie(accessToken);
    response.addCookie(refreshToken);
  }
}
