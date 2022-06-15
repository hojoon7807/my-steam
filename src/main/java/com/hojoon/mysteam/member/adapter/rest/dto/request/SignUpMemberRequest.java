package com.hojoon.mysteam.member.adapter.rest.dto.request;

import com.hojoon.mysteam.member.application.usecase.command.CreateMemberCommand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpMemberRequest {

  private String email;
  private String name;
  private String password;
  private String phoneNum;

  public SignUpMemberRequest(String email, String name, String password, String phoneNum) {
    this.email = email;
    this.name = name;
    this.password = password;
    this.phoneNum = phoneNum;
  }

  public CreateMemberCommand toCommand() {
    return CreateMemberCommand.builder()
        .email(email)
        .name(name)
        .password(password)
        .phoneNum(phoneNum)
        .build();
  }
}
