package com.hojoon.mysteam.member.application.usecase.impl;

import com.hojoon.mysteam.member.application.usecase.command.CreateMemberCommand;
import com.hojoon.mysteam.member.domain.model.Member;
import com.hojoon.mysteam.member.domain.model.Role;
import com.hojoon.mysteam.member.domain.repository.FindMemberPort;
import com.hojoon.mysteam.member.domain.repository.SaveNewMemberPort;
import com.hojoon.mysteam.member.exception.DuplicatedMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMemberService implements
    com.hojoon.mysteam.member.application.usecase.CreateMemberUsecase {

  private final SaveNewMemberPort saveNewMemberPort;
  private final FindMemberPort findMemberPort;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Member apply(CreateMemberCommand createMemberCommand) {
    findMemberPort.findMemberByEmail(createMemberCommand.getEmail())
        .orElseThrow(() -> new DuplicatedMemberException("이미 존재하는 회원입니다."));

    Member member = Member.builder()
        .email(createMemberCommand.getEmail())
        .name(passwordEncoder.encode(createMemberCommand.getName()))
        .password(createMemberCommand.getPassword())
        .phoneNum(createMemberCommand.getPhoneNum())
        .role(Role.USER)
        .build();

    return saveNewMemberPort.saveNewMember(member);
  }
}
