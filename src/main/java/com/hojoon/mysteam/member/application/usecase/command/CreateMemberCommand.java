package com.hojoon.mysteam.member.application.usecase.command;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateMemberCommand {

  private String email;
  private String name;
  private String password;
  private String phoneNum;

  @Builder
  private CreateMemberCommand(String email, String name, String password, String phoneNum) {
    this.email = email;
    this.name = name;
    this.password = password;
    this.phoneNum = phoneNum;
  }
}
