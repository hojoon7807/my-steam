package com.hojoon.mysteam.security.jwt;

import lombok.Getter;

@Getter
public enum JwtExpiration {
  ACCESS_TOKEN_EXPIRATION_TIME("accessToken", 30 * 60 * 1000L),
  REFRESH_TOKEN_EXPIRATION_TIME("refreshToken", 7 * 24 * 60 * 60 * 1000L);

  JwtExpiration(String tokenType, Long expiration) {
    this.tokenType = tokenType;
    this.expiration = expiration;
  }

  private String tokenType;
  private Long expiration;
}
