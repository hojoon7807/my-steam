package com.hojoon.mysteam.member.domain.repository;

import com.hojoon.mysteam.member.domain.model.Member;

public interface SaveNewMemberPort {

  Member saveNewMember(Member member);
}
