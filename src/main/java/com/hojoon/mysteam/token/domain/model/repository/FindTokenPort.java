package com.hojoon.mysteam.token.domain.model.repository;

import com.hojoon.mysteam.token.domain.model.RefreshToken;
import java.util.Optional;

public interface FindTokenPort {
  Optional<RefreshToken> findTokenByUserEmail(String email);
}
