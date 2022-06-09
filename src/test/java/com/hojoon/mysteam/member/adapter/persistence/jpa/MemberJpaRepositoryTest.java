package com.hojoon.mysteam.member.adapter.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.hojoon.mysteam.common.AbstractContainerBaseTest;
import com.hojoon.mysteam.common.TCDataJpaTest;
import com.hojoon.mysteam.member.adapter.persistence.jpa.model.MemberJpaEntity;
import com.hojoon.mysteam.member.domain.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@TCDataJpaTest
@Slf4j
public class MemberJpaRepositoryTest extends AbstractContainerBaseTest {

  @Autowired
  private MemberJpaRepository memberJpaRepository;

  @Test
  @DisplayName("유저 정보가 유효하면 해당 정보를 성공적으로 저장한다")
  void save() {
    final String USERNAME = "hojoon";
    final String EMAIL = "123@gmail.com";
    final String PASSWORD = "1234";

    MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
        .email(EMAIL)
        .name(USERNAME)
        .password(PASSWORD)
        .role(Role.USER)
        .phoneNum("01023121234")
        .build();

    MemberJpaEntity savedMember = memberJpaRepository.save(memberJpaEntity);

    log.info("member id: {}", savedMember.getId());

    assertAll(
        () -> assertThat(savedMember.getEmail()).isEqualTo(EMAIL),
        () -> assertThat(savedMember.getName()).isEqualTo(USERNAME),
        () -> assertThat(savedMember.getPassword()).isEqualTo(PASSWORD)
    );
  }
}
