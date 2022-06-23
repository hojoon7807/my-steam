package com.hojoon.mysteam.security.service;

import com.hojoon.mysteam.member.domain.model.Member;
import com.hojoon.mysteam.member.domain.repository.FindMemberPort;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Qualifier("memberDetailsService")
@Slf4j
public class CustomMemberDetailsService implements UserDetailsService {

  private final FindMemberPort findMemberPort;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("userDetailsService is run");

    return findMemberPort.findMemberByEmail(username)
        .map(this::createUserDetails)
        .orElseThrow(() ->
            new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
  }

  private UserDetails createUserDetails(Member member) {
    SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
        member.getRole().toString());

    return new User(member.getEmail(), member.getPassword(),
        Collections.singleton(grantedAuthority));
  }
}
