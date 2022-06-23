package com.hojoon.mysteam.token.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefreshToken {
  private Long id;
  private String email;
  private String refreshToken;
  private String expiration;

  @Builder
  private RefreshToken(Long id, String email, String refreshToken, String expiration) {
    this.id = id;
    this.email = email;
    this.refreshToken = refreshToken;
    this.expiration = expiration;
  }
}
