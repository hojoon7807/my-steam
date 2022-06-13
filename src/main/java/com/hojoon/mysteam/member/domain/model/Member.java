package com.hojoon.mysteam.member.domain.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

  private Long id;
  private String name;
  private String email;
  private String password;
  private String phoneNum;
  private Role role;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  @Builder
  private Member(Long id, String name, String email, String password, String phoneNum,
      LocalDateTime createdAt, LocalDateTime modifiedAt, Role role) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.phoneNum = phoneNum;
    this.role = role;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }
}
