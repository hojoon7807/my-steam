package com.hojoon.mysteam.member.adapter.rest.dto.request;

import com.hojoon.mysteam.member.application.usecase.command.SignInMemberCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInMemberRequest {

  private String email;
  private String password;

  public SignInMemberRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public SignInMemberCommand toCommand() {
    return new SignInMemberCommand(email, password);
  }
}
