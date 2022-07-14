package com.hojoon.mysteam.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hojoon.mysteam.util.CookieUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class JwtAuthorizationFilterTest {

  @Mock
  private JwtProvider jwtProvider;

  @Mock
  private CookieUtil cookieUtil;

  @InjectMocks
  private JwtAuthorizationFilter jwtAuthorizationFilter;

  private MockHttpServletRequest request;

  private MockHttpServletResponse response;

  private MockFilterChain chain;

  @BeforeEach
  void beforeEach() {
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
    chain = new MockFilterChain();
  }

  @Test
  @DisplayName("/signin 으로 접근시 해당 필터가 작동하지 않는다")
  void dontWorkFilterWithSigninUri() throws ServletException, IOException {
    JwtAuthorizationFilter spy = spy(jwtAuthorizationFilter);
    request.setServletPath("/signin");

    spy.doFilter(request, response, chain);

    verify(spy, times(1)).shouldNotFilter(request);
    verify(spy, never()).doFilterInternal(request, response, chain);
  }

  @Test
  @DisplayName("/signup 으로 접근시 해당 필터가 작동하지 않는다")
  void dontWorkFilterWithSignupUri() throws ServletException, IOException {
    JwtAuthorizationFilter spy = spy(jwtAuthorizationFilter);
    request.setServletPath("/signup");
    spy.doFilter(request, response, chain);

    verify(spy, times(1)).shouldNotFilter(request);
    verify(spy, never()).doFilterInternal(request, response, chain);
  }

  @Test
  @DisplayName("허용된 URI로 접근시 해당 필터가 작동한다")
  void doWorkFilterWithAllowedUri() throws ServletException, IOException {
    JwtAuthorizationFilter spy = spy(jwtAuthorizationFilter);
    request.setServletPath("/");
    spy.doFilter(request, response, chain);

    verify(spy, times(1)).shouldNotFilter(request);
    verify(spy, times(1)).doFilterInternal(request, response, chain);
  }

  @Test
  @DisplayName("토큰 정보가 존재하지 않으면 인증을 진행할 수 없다")
  void doFilterInternalWithoutToken() throws ServletException, IOException {
    request.setRequestURI("/");

    when(cookieUtil.getCookie(eq(request), anyString())).thenReturn(null);

    jwtAuthorizationFilter.doFilterInternal(request, response, chain);

    verify(jwtProvider, never()).getAuthentication(anyString());
  }

  @Test
  @DisplayName("토큰 정보가 유효하지 않으면 인증을 진행할 수 없다")
  void doFilterInternalWithInvalidToken() throws ServletException, IOException {
    request.setRequestURI("/");

    when(cookieUtil.getCookie(eq(request), anyString())).thenReturn(
        new Cookie("accessToken", "invalidToken"));
    when(jwtProvider.validateToken(anyString())).thenReturn(false);

    jwtAuthorizationFilter.doFilterInternal(request, response, chain);

    verify(jwtProvider, never()).getAuthentication(anyString());
  }

  @Test
  @DisplayName("토큰 정보가 유효하면 성공적으로 인증정보를 저장한다")
  void doFilterInternalWithValidToken() throws ServletException, IOException {
    request.setRequestURI("/");

    Authentication authentication = new TestingAuthenticationToken("test", null, "USER");
    when(cookieUtil.getCookie(eq(request), anyString())).thenReturn(
        new Cookie("accessToken", "validToken"));
    when(jwtProvider.validateToken(anyString())).thenReturn(true);
    when(jwtProvider.getAuthentication(anyString())).thenReturn(authentication);

    jwtAuthorizationFilter.doFilterInternal(request, response, chain);

    assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
  }
}