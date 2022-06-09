package com.hojoon.mysteam.member.adapter.persistence.jpa.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.hojoon.mysteam.member.adapter.persistence.jpa.model.MemberJpaEntity;
import com.hojoon.mysteam.member.domain.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberJpaEntityTest {

  @Test
  @DisplayName("MemberJpaEntity 생성 테스트")
  void buildMemberJpaEntity() {
    MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
        .email("1234@gmail.com")
        .name("hojoon")
        .phoneNum("01012341234")
        .password("1234")
        .role(Role.USER)
        .build();

    assertThat(memberJpaEntity).isInstanceOf(MemberJpaEntity.class);
  }
}
