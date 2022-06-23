package com.hojoon.mysteam.token.application.adapter.persistence.jpa;

import com.hojoon.mysteam.token.application.adapter.persistence.jpa.model.RefreshTokenJpaEntity;
import com.hojoon.mysteam.token.domain.model.RefreshToken;
import com.hojoon.mysteam.token.domain.model.repository.FindTokenPort;
import com.hojoon.mysteam.token.domain.model.repository.SaveTokenPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshToeknJpaAdapter implements SaveTokenPort, FindTokenPort {

  private final RefreshTokenJpaRepository refreshTokenJpaRepository;
  private final RefreshTokenMapper refreshTokenMapper;

  @Override
  public Optional<RefreshToken> findTokenByUserEmail(
      String email) {
    return refreshTokenJpaRepository.findByEmail(email).map(
        refreshTokenJpaEntity -> refreshTokenMapper.toDomain(refreshTokenJpaEntity)
    );
  }

  @Override
  public RefreshToken saveToken(RefreshToken refreshToken) {
    RefreshTokenJpaEntity refreshTokenJpaEntity = refreshTokenJpaRepository.save(
        refreshTokenMapper.toJpaEntity(refreshToken));

    return refreshTokenMapper.toDomain(refreshTokenJpaEntity);
  }
}
