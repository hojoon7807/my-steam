package com.hojoon.mysteam.member.adapter.persistence.jpa;

import com.hojoon.mysteam.member.adapter.persistence.jpa.model.MemberJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {

  Optional<MemberJpaEntity> findByName(String name);

  Optional<MemberJpaEntity> findByEmail(String email);
}
