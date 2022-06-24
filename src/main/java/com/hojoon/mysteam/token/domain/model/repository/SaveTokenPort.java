package com.hojoon.mysteam.token.domain.model.repository;

import com.hojoon.mysteam.token.domain.model.RefreshToken;

public interface SaveTokenPort {

  RefreshToken saveToken(RefreshToken refreshToken);
}
