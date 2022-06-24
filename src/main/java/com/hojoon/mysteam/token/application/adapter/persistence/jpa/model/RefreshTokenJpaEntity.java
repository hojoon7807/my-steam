package com.hojoon.mysteam.token.application.adapter.persistence.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshTokenJpaEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column
  private String email;
  @Column(name = "refresh_token")
  private String refreshToken;
  @Column
  private String expiration;

  public RefreshTokenJpaEntity(String email, String refreshToken, String expiration) {
    this.email = email;
    this.refreshToken = refreshToken;
    this.expiration = expiration;
  }
}
