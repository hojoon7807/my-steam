package com.hojoon.mysteam.member.domain.repository;

public interface MemberRepository<T, ID> {

  T save(T t);

  T findById(ID id);

  void delete(ID id);
}
