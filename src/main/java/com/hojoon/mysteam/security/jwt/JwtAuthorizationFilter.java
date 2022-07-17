package com.hojoon.mysteam.security.jwt;

import com.hojoon.mysteam.util.CookieUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final CookieUtil cookieUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String token = resolveToken(request, JwtExpiration.ACCESS_TOKEN_EXPIRATION_TIME.getTokenType());
    if (token != null && jwtProvider.validateToken(token)) {
      Authentication authentication = jwtProvider.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(),
          request.getRequestURI());
    } else {
      log.info("유효한 JWT 토큰이 없습니다, uri: {}", request.getRequestURI());
    }
    filterChain.doFilter(request, response);
  }

  private String resolveToken(HttpServletRequest request, String tokenName) {
    Cookie cookie = cookieUtil.getCookie(request, tokenName);
    return cookie != null ? cookie.getValue() : null;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return(new AntPathMatcher().match("/signin", request.getServletPath())
        || new AntPathMatcher().match("/signup", request.getServletPath()));
  }
}
