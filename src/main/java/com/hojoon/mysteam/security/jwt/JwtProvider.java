package com.hojoon.mysteam.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider {

  private String SECRET_KEY;
  private final String AUTHORITIES_KEY = "auth";

  public JwtProvider(@Value("${jwt.secret}") String SECRET_KEY) {
    this.SECRET_KEY = SECRET_KEY;
  }

  private Claims parseClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build()
        .parseClaimsJws(token).getBody();
  }

  public Authentication getAuthentication(String accessToken) {
    Claims claims = parseClaims(accessToken);

    List<SimpleGrantedAuthority> authorities = Arrays.stream(
            claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    UserDetails principal = new User(claims.getSubject(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }

  private Key getSigningKey(String secretKey) {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String generateAccessToken(Authentication authentication) {
    return doGenerateToken(authentication,
        JwtExpiration.ACCESS_TOKEN_EXPIRATION_TIME.getExpiration());
  }

  public String generateRefreshToken(Authentication authentication) {
    return doGenerateToken(authentication,
        JwtExpiration.REFRESH_TOKEN_EXPIRATION_TIME.getExpiration());
  }

  private String doGenerateToken(Authentication authentication, long expireTime) {
    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

    Date now = new Date();

    return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
        .setIssuedAt(now).setExpiration(new Date(now.getTime() + expireTime))
        .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256).compact();
  }

  public boolean validateToken(String token) {
    try {
      parseClaims(token);
      return true;
    } catch (Exception e) {
      log.info(e.getMessage());
    }
    return false;
  }
}
