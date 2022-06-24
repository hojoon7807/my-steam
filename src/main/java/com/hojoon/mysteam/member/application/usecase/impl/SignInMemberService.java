package com.hojoon.mysteam.member.application.usecase.impl;

import com.hojoon.mysteam.member.application.usecase.SignInMemberUsecase;
import com.hojoon.mysteam.member.application.usecase.command.SignInMemberCommand;
import com.hojoon.mysteam.security.jwt.JwtProvider;
import com.hojoon.mysteam.token.application.dto.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignInMemberService implements SignInMemberUsecase {

  private final JwtProvider jwtProvider;
  private final AuthenticationManager authenticationManager;

  @Transactional
  @Override
  public TokenInfo apply(SignInMemberCommand signInMemberCommand) {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(
            signInMemberCommand.getEmail(), signInMemberCommand.getPassword());
    Authentication authentication = authenticationManager.authenticate(authenticationToken);

    String accessToken = jwtProvider.generateAccessToken(authentication);
    String refreshToken = jwtProvider.generateRefreshToken(authentication);
    return TokenInfo.of(accessToken, refreshToken);
  }
}
