package com.hojoon.mysteam.security.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.hojoon.mysteam.member.domain.model.Member;
import com.hojoon.mysteam.member.domain.model.Role;
import com.hojoon.mysteam.member.domain.repository.FindMemberPort;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class CustomMemberDetailsServiceTest {

  @InjectMocks
  private CustomMemberDetailsService customMemberDetailsService;

  @Mock
  private FindMemberPort findMemberPort;

  @Test
  @DisplayName("DB에 이메일에 해당 하는 맴버가 존재하면 UserDetails 객체를 반환한다")
  void loadUserDetailsByUsername() {
    String EMAIL = "test@gmail.com";
    Member member = Member.builder().email(EMAIL).password("1234").role(Role.USER).build();

    when(findMemberPort.findMemberByEmail(EMAIL)).thenReturn(Optional.of(member));

    UserDetails userDetails = customMemberDetailsService.loadUserByUsername(EMAIL);

    assertAll(() -> assertThat(userDetails).isNotNull(),
        () -> assertThat(userDetails.getUsername()).isEqualTo(EMAIL));
  }

  @Test
  @DisplayName("DB에 이메일에 해당 하는 맴버가 존재하지 않으면 UsernameNotFoundException 예외가 발생한다")
  void loadUserDetailsByUsername_usernameNotFoundException() {
    String EMAIL = "test@gmail.com";
    Member member = Member.builder().email(EMAIL).password("1234").role(Role.USER).build();

    when(findMemberPort.findMemberByEmail(EMAIL)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> customMemberDetailsService.loadUserByUsername(EMAIL)).isInstanceOf(
        UsernameNotFoundException.class).hasMessage("해당하는 유저를 찾을 수 없습니다.");
  }
}