package com.hojoon.mysteam.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

public class JwtProvider {

  @Value("${jwt.secret}")
  private String SECRET_KEY;

  private long ACCESS_EXPIRATION_TIME = 30 * 60 * 1000L;
  private long REFRESH_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000L;

  public Claims extractClaims(String token){
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey(SECRET_KEY))
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public String getUserEmail(String token){
    return extractClaims(token).get("email", String.class);
  }

  private Key getSigningKey(String secretKey){
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String generateAccessToken(String email){
    return doGenerateToken(email, ACCESS_EXPIRATION_TIME);
  }

  public String generateRefreshToken(String email){
    return doGenerateToken(email, REFRESH_EXPIRATION_TIME);
  }

  private String doGenerateToken(String email, long expireTime){
    Claims claims = Jwts.claims();
    claims.put("email", email);
    Date now = new Date();
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + expireTime))
        .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
        .compact();
  }
}
