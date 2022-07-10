package com.hojoon.mysteam.member.application.usecase.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hojoon.mysteam.member.application.usecase.command.CreateMemberCommand;
import com.hojoon.mysteam.member.application.usecase.exception.DuplicatedMemberException;
import com.hojoon.mysteam.member.domain.model.Member;
import com.hojoon.mysteam.member.domain.model.Role;
import com.hojoon.mysteam.member.domain.repository.FindMemberPort;
import com.hojoon.mysteam.member.domain.repository.SaveNewMemberPort;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CreateMemberServiceTest {

  @InjectMocks
  private CreateMemberService createMemberService;

  @Mock
  private SaveNewMemberPort saveNewMemberPort;

  @Mock
  private FindMemberPort findMemberPort;

  @Spy
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("요청받은 회원가입 정보를 성공적으로 저장하고 Member 객체를 반환한다")
  void createNewMember() {
    passwordEncoder = new BCryptPasswordEncoder();

    CreateMemberCommand request = CreateMemberCommand.builder().email("hojoon@gmail.com")
        .name("hojoon").phoneNum("01012341234").password("1234").build();

    String encodedPassword = passwordEncoder.encode(request.getPassword());

    Member member = Member.builder().id(1L).email(request.getEmail()).name(request.getName())
        .role(Role.USER).phoneNum(request.getPhoneNum()).password(encodedPassword).build();

    when(findMemberPort.findMemberByEmail(any(String.class))).thenReturn(Optional.empty());
    when(saveNewMemberPort.saveNewMember(any())).thenReturn(member);

    Member savedMember = createMemberService.apply(request);

    assertAll(() -> assertThat(savedMember.getEmail()).isEqualTo(request.getEmail()),
        () -> assertThat(
            passwordEncoder.matches(request.getPassword(), savedMember.getPassword())).isTrue(),
        () -> verify(findMemberPort, times(1)).findMemberByEmail(any(String.class)),
        () -> verify(saveNewMemberPort, times(1)).saveNewMember(any(Member.class)));
  }

  @Test
  @DisplayName("이미 존재하는 회원이 있으면 DuplicatedMember 예외가 발생한다")
  void createNewMember_DuplicatedMember() {
    Member member = Member.builder().email("ex").build();
    when(findMemberPort.findMemberByEmail(any(String.class))).thenReturn(Optional.of(member));

    assertThatThrownBy(() -> createMemberService.apply(
        CreateMemberCommand.builder().email("ex").build())).isInstanceOf(
        DuplicatedMemberException.class).hasMessage("이미 존재하는 회원입니다.");
    assertAll(() -> verify(findMemberPort, times(1)).findMemberByEmail(any(String.class)),
        () -> verify(saveNewMemberPort, never()).saveNewMember(any(Member.class)));
  }
}