package com.hojoon.mysteam.member.adapter.rest.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInMemberRequest {

  private String email;
  private String password;
}
