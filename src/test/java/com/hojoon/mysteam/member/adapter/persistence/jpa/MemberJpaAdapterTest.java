package com.hojoon.mysteam.member.adapter.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.hojoon.mysteam.common.AbstractContainerBaseTest;
import com.hojoon.mysteam.common.TCDataJpaTest;
import com.hojoon.mysteam.member.domain.model.Member;
import com.hojoon.mysteam.member.domain.model.Role;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@TCDataJpaTest
@Slf4j
@Import({MemberJpaAdapter.class, MemberMapper.class})
public class MemberJpaAdapterTest extends AbstractContainerBaseTest {

  @Autowired
  private MemberJpaAdapter memberJpaAdapter;

  @Test
  @DisplayName("멤버 정보가 유효하면 해당 객체를 성공적으로 저장하고 반환한다")
  void saveNewMember() {
    Member member = Member.builder()
        .role(Role.USER)
        .email("1234@gmail.com")
        .name("hojoon")
        .password("1234")
        .phoneNum("01012341234")
        .build();

    Member savedMember = memberJpaAdapter.saveNewMember(member);
    log.info("member id: {}", savedMember.getId());

    assertThat(savedMember).isNotNull();
  }

  @Test
  @Sql("MemberJpaAdapterTest.sql")
  @DisplayName("ID 정보가 유효하면 id에 해당하는 객체를 성공적으로 반환한다")
  void findByMemberId() {
    final Long ID = 1L;

    Optional<Member> member = memberJpaAdapter.findMemberById(ID);
    log.info("member id: {}", member.get().getId());

    assertThat(member).isPresent().hasValueSatisfying(existMember ->
        assertThat(existMember.getId()).isEqualTo(ID)
    );
  }

  @Test
  @DisplayName("ID 정보가 유효하지 않으면 null을 반환한다")
  void findByNotSavedMemberId() {
    final Long ID = 1L;

    Optional<Member> member = memberJpaAdapter.findMemberById(ID);

    assertThat(member).isEmpty();
  }

  @Test
  @Sql("MemberJpaAdapterTest.sql")
  @DisplayName("email 정보가 유효하면 email에 해당하는 객체를 성공적으로 반환한다")
  void findByMemberEmail() {
    final String EMAIL = "hojoon@gmail.com";

    Optional<Member> member = memberJpaAdapter.findMemberByEmail(EMAIL);
    log.info("member id: {}", member.get().getId());

    assertThat(member).isPresent().hasValueSatisfying(existMember ->
        assertThat(existMember.getEmail()).isEqualTo(EMAIL)
    );
  }

  @Test
  @DisplayName("email 정보가 유효하지 않으면 null을 반환한다")
  void findByNotSavedMemberEmail() {
    final String EMAIL = "hojoon@gmail.com";

    Optional<Member> member = memberJpaAdapter.findMemberByEmail(EMAIL);

    assertThat(member).isEmpty();
  }
}
