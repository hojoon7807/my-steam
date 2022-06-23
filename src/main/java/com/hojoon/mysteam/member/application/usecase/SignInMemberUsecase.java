package com.hojoon.mysteam.member.application.usecase;

import com.hojoon.mysteam.member.application.usecase.command.SignInMemberCommand;
import com.hojoon.mysteam.token.application.dto.TokenInfo;
import java.util.function.Function;

public interface SignInMemberUsecase extends Function<SignInMemberCommand, TokenInfo> {

  @Override
  TokenInfo apply(SignInMemberCommand signInMemberCommand);
}
