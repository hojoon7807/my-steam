package com.hojoon.mysteam.token.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenInfo {
  private String grantType;
  private String accessToken;
  private String refreshToken;

  public static TokenInfo of(String accessToken, String refreshToken) {
    return new TokenInfo("Bearer ", accessToken, refreshToken);
  }
}
