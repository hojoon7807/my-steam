package com.hojoon.mysteam.member.domain.repository;

import com.hojoon.mysteam.member.domain.model.Member;
import java.util.Optional;

public interface MemberRepository {

  Member save(Member member);

  Optional<Member> findById(Long id);

  Optional<Member> findByEmail(String email);

  void delete(Long id);
}
