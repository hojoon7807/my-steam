package com.hojoon.mysteam.token.application.adapter.persistence.jpa;

import com.hojoon.mysteam.token.application.adapter.persistence.jpa.model.RefreshTokenJpaEntity;
import com.hojoon.mysteam.token.domain.model.RefreshToken;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {

  public RefreshToken toDomain(RefreshTokenJpaEntity refreshTokenJpaEntity) {
    return RefreshToken.builder()
        .id(refreshTokenJpaEntity.getId())
        .email(refreshTokenJpaEntity.getEmail())
        .refreshToken(refreshTokenJpaEntity.getRefreshToken())
        .expiration(refreshTokenJpaEntity.getExpiration())
        .build();
  }

  public RefreshTokenJpaEntity toJpaEntity(RefreshToken refreshToken) {
    return new RefreshTokenJpaEntity(refreshToken.getEmail(), refreshToken.getRefreshToken(),
        refreshToken.getExpiration());
  }
}
