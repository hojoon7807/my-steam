package com.hojoon.mysteam.member.application.usecase.impl;

import com.hojoon.mysteam.member.application.usecase.command.CreateMemberCommand;
import com.hojoon.mysteam.member.application.usecase.exception.DuplicatedMemberException;
import com.hojoon.mysteam.member.domain.model.Member;
import com.hojoon.mysteam.member.domain.model.Role;
import com.hojoon.mysteam.member.domain.repository.FindMemberPort;
import com.hojoon.mysteam.member.domain.repository.SaveNewMemberPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateMemberService implements
    com.hojoon.mysteam.member.application.usecase.CreateMemberUsecase {

  private final SaveNewMemberPort saveNewMemberPort;
  private final FindMemberPort findMemberPort;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public Member apply(CreateMemberCommand createMemberCommand) {
    checkDuplicatedMember(createMemberCommand.getEmail());

    Member member = Member.builder()
        .email(createMemberCommand.getEmail())
        .name(createMemberCommand.getName())
        .password(passwordEncoder.encode(createMemberCommand.getPassword()))
        .phoneNum(createMemberCommand.getPhoneNum())
        .role(Role.USER)
        .build();

    return saveNewMemberPort.saveNewMember(member);
  }

  private void checkDuplicatedMember(String email) {
    findMemberPort.findMemberByEmail(email).ifPresent(member -> {
      throw new DuplicatedMemberException("이미 존재하는 회원입니다.");
    });
  }
}
