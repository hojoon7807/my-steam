package com.hojoon.mysteam.config.security;

import com.hojoon.mysteam.member.domain.model.Member;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(
      String username) throws UsernameNotFoundException {
    return null;
  }

  private UserDetails createUserDetails(Member member) {
    SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
        member.getRole().toString());

    return new User(member.getEmail(), member.getPassword(),
        Collections.singleton(grantedAuthority));
  }
}
