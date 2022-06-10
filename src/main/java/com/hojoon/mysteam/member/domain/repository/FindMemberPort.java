package com.hojoon.mysteam.member.domain.repository;

import com.hojoon.mysteam.member.domain.model.Member;
import java.util.Optional;

public interface FindMemberPort {
  Optional<Member> findMemberById(Long id);

  Optional<Member> findMemberByEmail(String email);
}
