package com.hojoon.mysteam.member.adapter.persistence.jpa;

import com.hojoon.mysteam.member.domain.model.Member;
import com.hojoon.mysteam.member.domain.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberJpaAdapter implements MemberRepository {
  private final MemberJpaRepository memberJpaRepository;
  private final MemberMapper memberMapper;

  @Override
  public Member save(Member member) {
    return null;
  }

  @Override
  public Optional<Member> findById(Long id) {
    return null;
  }

  @Override
  public Optional<Member> findByEmail(String email) {
    return Optional.empty();
  }

  @Override
  public void delete(Long id) {

  }
}
