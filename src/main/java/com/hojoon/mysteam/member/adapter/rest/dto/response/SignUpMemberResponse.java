package com.hojoon.mysteam.member.adapter.rest.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpMemberResponse {
  private String email;

  public SignUpMemberResponse(String email) {
    this.email = email;
  }
}
