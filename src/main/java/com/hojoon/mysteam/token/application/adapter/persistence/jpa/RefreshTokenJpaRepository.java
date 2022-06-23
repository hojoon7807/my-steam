package com.hojoon.mysteam.token.application.adapter.persistence.jpa;

import com.hojoon.mysteam.token.application.adapter.persistence.jpa.model.RefreshTokenJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, Long> {
  Optional<RefreshTokenJpaEntity> findByEmail(String email);
}
