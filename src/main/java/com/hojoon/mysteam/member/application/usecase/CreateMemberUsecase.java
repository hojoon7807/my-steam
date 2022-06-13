package com.hojoon.mysteam.member.application.usecase;

import com.hojoon.mysteam.member.application.usecase.command.CreateMemberCommand;
import com.hojoon.mysteam.member.domain.model.Member;
import java.util.function.Function;

public interface CreateMemberUsecase extends Function<CreateMemberCommand, Member> {

  @Override
  Member apply(CreateMemberCommand createMemberCommand);
}
