package com.hojoon.mysteam.member.adapter.persistence.jpa;

import com.hojoon.mysteam.member.adapter.persistence.jpa.model.MemberJpaEntity;
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
    MemberJpaEntity memberJpaEntity = memberJpaRepository.save(memberMapper.toJpaEntity(member));
    return memberMapper.toDomain(memberJpaEntity);
  }

  @Override
  public Optional<Member> findById(Long id) {
    return memberJpaRepository.findById(id)
        .map(memberJpaEntity -> memberMapper.toDomain(memberJpaEntity));
  }

  @Override
  public Optional<Member> findByEmail(String email) {
    return memberJpaRepository.findByEmail(email)
        .map(memberJpaEntity -> memberMapper.toDomain(memberJpaEntity));
  }

  @Override
  public void delete(Long id) {
    memberJpaRepository.findById(id)
        .ifPresent(memberJpaEntity -> memberJpaRepository.delete(memberJpaEntity));
  }
}
