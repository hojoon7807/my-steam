package com.hojoon.mysteam.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.hojoon.mysteam.security.jwt.JwtProvider;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

class JwtProviderTest {

  private JwtProvider jwtProvider;

  @BeforeEach
  void init() {
    jwtProvider = new JwtProvider("secretKey-test-jwt-for-mysteam-project");
  }

  @Test
  @DisplayName("인증 객체를 이용해 성공적으로 access token를 생성하고 반환한다")
  void generateAccessToken() {
    Authentication authenticationToken = new TestingAuthenticationToken("test", "password", "USER");

    String accessToken = jwtProvider.generateAccessToken(authenticationToken);

    assertThat(accessToken).isNotNull();
  }

  @Test
  @DisplayName("인증 객체를 이용해 성공적으로 refresh token를 생성하고 반환한다")
  void generateRefreshToken() {
    Authentication authenticationToken = new TestingAuthenticationToken("test", "password", "USER");

    String refreshToken = jwtProvider.generateRefreshToken(authenticationToken);

    assertThat(refreshToken).isNotNull();
  }

  @Test
  @DisplayName("토큰정보가 유효하면 성공적으로 Authentication 객체를 반환한다")
  void getAuthentication() {
    Authentication authenticationToken = new TestingAuthenticationToken("test", "password", "USER");
    String accessToken = jwtProvider.generateAccessToken(authenticationToken);

    Authentication authentication = jwtProvider.getAuthentication(accessToken);

    assertThat(authentication.getAuthorities()).isEqualTo(authenticationToken.getAuthorities());
  }

  @Test
  @DisplayName("올바른 토큰이 주어지면 토큰 유효성 검사시 true를 반환한다")
  void validateWithValidToken() {
    Authentication authenticationToken = new TestingAuthenticationToken("test", "password", "USER");
    String token = jwtProvider.generateAccessToken(authenticationToken);

    boolean result = jwtProvider.validateToken(token);

    assertThat(result).isTrue();
  }

  @Test
  @DisplayName("올바른 토큰이 주어지면 토큰 유효성 검사시 false를 반환한다")
  void validateWithInvalidToken() {
    Date now = new Date();
    String token = Jwts.builder().setExpiration(new Date(now.getTime() - 1000L * 60)).compact();

    boolean result = jwtProvider.validateToken(token);

    assertThat(result).isFalse();
  }
}