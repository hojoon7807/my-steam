package com.hojoon.mysteam.member.adapter.rest.dto.request;

import com.hojoon.mysteam.member.application.usecase.command.CreateMemberCommand;
import lombok.Getter;

@Getter
public class SignUpMemberRequest {

  private String email;
  private String name;
  private String password;
  private String phoneNum;

  public CreateMemberCommand toCommand() {
    return CreateMemberCommand.builder()
        .email(email)
        .name(name)
        .password(password)
        .phoneNum(phoneNum)
        .build();
  }
}
