package com.hojoon.mysteam.member.adapter.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.hojoon.mysteam.member.adapter.persistence.jpa.model.MemberJpaEntity;
import com.hojoon.mysteam.member.domain.model.Member;
import com.hojoon.mysteam.member.domain.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberMapperTest {

  private MemberMapper memberMapper = new MemberMapper();

  @Test
  @DisplayName("정상적인 Member객체가 주어지면 MemberJpaEntity로 매핑한 객체를 성공적으로 반환한다")
  void memberToMemberJpaEntity() {
    Member member = Member.builder()
        .role(Role.USER)
        .id(1L)
        .email("1234@gmail.com")
        .name("hojoon")
        .password("1234")
        .phoneNum("01012341234")
        .build();

    MemberJpaEntity memberJpaEntity = memberMapper.toJpaEntity(member);

    assertAll(
        () -> assertThat(memberJpaEntity).isInstanceOf(MemberJpaEntity.class),
        () -> assertThat(memberJpaEntity.getName()).isEqualTo(member.getName())
    );
  }

  @Test
  @DisplayName("정상적인 MemberJpaEntity 객체가 주어지면 Member로 매핑한 객체를 성공적으로 반환한다")
  void memberJpaEntityToMember() {
    MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
        .id(1L)
        .email("1234@gmail.com")
        .name("hojoon")
        .phoneNum("01012341234")
        .password("1234")
        .role(Role.USER)
        .build();

    Member member = memberMapper.toDomain(memberJpaEntity);

    assertAll(
        () -> assertThat(member).isInstanceOf(Member.class),
        () -> assertThat(member.getName()).isEqualTo(memberJpaEntity.getName())
    );
  }
}
