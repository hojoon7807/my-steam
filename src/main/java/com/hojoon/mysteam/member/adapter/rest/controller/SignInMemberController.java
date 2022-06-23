package com.hojoon.mysteam.member.adapter.rest.controller;

import com.hojoon.mysteam.member.adapter.rest.dto.request.SignInMemberRequest;
import com.hojoon.mysteam.member.application.usecase.SignInMemberUsecase;
import com.hojoon.mysteam.security.jwt.JwtExpiration;
import com.hojoon.mysteam.token.application.dto.TokenInfo;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignInMemberController {

  private final SignInMemberUsecase signInMemberUsecase;

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody SignInMemberRequest signInMemberRequest,
      HttpServletResponse res) {
    TokenInfo tokenInfo = signInMemberUsecase.apply(signInMemberRequest.toCommand());

    Cookie accessToken = new Cookie("accessToken", tokenInfo.getAccessToken());
    Cookie refreshToken = new Cookie("refreshToken", tokenInfo.getRefreshToken());
    accessToken.setMaxAge(
        (int) (JwtExpiration.ACCESS_TOKEN_EXPIRATION_TIME.getExpiration() / 1000L));
    refreshToken.setMaxAge(
        (int) (JwtExpiration.REFRESH_TOKEN_EXPIRATION_TIME.getExpiration() / 1000L));

    res.addCookie(accessToken);
    res.addCookie(refreshToken);

    return ResponseEntity.ok(null);
  }
}
