package com.hojoon.mysteam.member.adapter.persistence.jpa;

import com.hojoon.mysteam.member.adapter.persistence.jpa.model.MemberJpaEntity;
import com.hojoon.mysteam.member.domain.model.Member;
import com.hojoon.mysteam.member.domain.repository.DeleteMemberPort;
import com.hojoon.mysteam.member.domain.repository.FindMemberPort;
import com.hojoon.mysteam.member.domain.repository.SaveNewMemberPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberJpaAdapter implements SaveNewMemberPort, DeleteMemberPort, FindMemberPort {

  private final MemberJpaRepository memberJpaRepository;
  private final MemberMapper memberMapper;

  @Override
  public Member saveNewMember(Member member) {
    MemberJpaEntity memberJpaEntity = memberJpaRepository.save(memberMapper.toJpaEntity(member));
    return memberMapper.toDomain(memberJpaEntity);
  }

  @Override
  public Optional<Member> findMemberById(Long id) {
    return memberJpaRepository.findById(id)
        .map(memberJpaEntity -> memberMapper.toDomain(memberJpaEntity));
  }

  @Override
  public Optional<Member> findMemberByEmail(String email) {
    return memberJpaRepository.findByEmail(email)
        .map(memberJpaEntity -> memberMapper.toDomain(memberJpaEntity));
  }

  @Override
  public void deleteMemberById(Long id) {
    memberJpaRepository.findById(id)
        .ifPresent(memberJpaEntity -> memberJpaRepository.delete(memberJpaEntity));
  }
}
