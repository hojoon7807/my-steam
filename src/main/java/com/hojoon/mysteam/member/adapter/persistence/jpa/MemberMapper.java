package com.hojoon.mysteam.member.adapter.persistence.jpa;

import com.hojoon.mysteam.member.adapter.persistence.jpa.model.MemberJpaEntity;
import com.hojoon.mysteam.member.domain.model.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

  public Member toDomain(MemberJpaEntity memberJpaEntity) {
    return Member.builder()
        .id(memberJpaEntity.getId())
        .password(memberJpaEntity.getPassword())
        .email(memberJpaEntity.getEmail())
        .name(memberJpaEntity.getName())
        .phoneNum(memberJpaEntity.getPhoneNum())
        .role(memberJpaEntity.getRole())
        .createdAt(memberJpaEntity.getCreatedAt())
        .modifiedAt(memberJpaEntity.getModifiedAt())
        .build();
  }

  public MemberJpaEntity toJpaEntity(Member member) {
    return MemberJpaEntity.builder()
        .id(member.getId())
        .email(member.getEmail())
        .password(member.getPassword())
        .phoneNum(member.getPhoneNum())
        .role(member.getRole())
        .name(member.getName())
        .build();
  }
}
