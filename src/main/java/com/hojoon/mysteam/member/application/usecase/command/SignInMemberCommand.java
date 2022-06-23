package com.hojoon.mysteam.member.application.usecase.command;

import lombok.Getter;

@Getter
public class SignInMemberCommand {
  private String email;
  private String password;

  public SignInMemberCommand(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
