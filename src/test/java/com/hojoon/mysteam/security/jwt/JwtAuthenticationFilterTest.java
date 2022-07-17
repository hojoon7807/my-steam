package com.hojoon.mysteam.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hojoon.mysteam.util.CookieUtil;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

  @Spy
  private CookieUtil cookieUtil;
  @Mock
  private JwtProvider jwtProvider;
  @Mock
  private AuthenticationManager authenticationManager;
  private MockHttpServletRequest request;
  private MockHttpServletResponse response;
  private MockFilterChain chain;
  @InjectMocks
  private static JwtAuthenticationFilter jwtAuthenticationFilter;

  @BeforeEach
  void beforeEach() {
    jwtAuthenticationFilter.setFilterProcessesUrl("/signin");
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
    chain = new MockFilterChain();
  }

  @Test
  @DisplayName("/api/signin 으로 접근하지 않으면 해당 필터의 메소드가 작동하지 않는다.")
  void dontWorkFilterMethodWithNotAllowedUri() throws ServletException, IOException {
    JwtAuthenticationFilter spy = spy(jwtAuthenticationFilter);
    request.setServletPath("/");

    spy.doFilter(request, response, chain);

    verify(spy, never()).attemptAuthentication(request, response);
    verify(spy, never()).successfulAuthentication(eq(request), eq(response), eq(chain),
        any(Authentication.class));
  }

  @Test
  @DisplayName("요청의 콘텐츠 정보가 읽을 수 없으면 RuntimeException이 발생한다")
  void authenticationWithNotReadableContentThrowRuntimeException() {
    request.setServletPath("/signip");

    assertThatThrownBy(() -> jwtAuthenticationFilter.attemptAuthentication(request, response))
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  @DisplayName("인증 객체가 존재하지 않으면 successfulAuthentication 메소드가 실행하지 않는다")
  void dontInvokeSuccessfulAuthenticationMethod() throws ServletException, IOException {
    JwtAuthenticationFilter spy = spy(jwtAuthenticationFilter);
    String EMAIL = "test@gmail.com";
    String PASSWORD = "1234";
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        EMAIL, PASSWORD);

    String signInInfo = "{ \"email\" : \"" + EMAIL + "\", \"password\" : \"" + PASSWORD + "\" }";
    request.setServletPath("/signin");
    request.setContent(signInInfo.getBytes());

    when(authenticationManager.authenticate(authenticationToken)).thenThrow(
        new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));

    spy.doFilter(request, response, chain);

    verify(spy, times(1)).attemptAuthentication(request, response);
    verify(spy, never()).successfulAuthentication(eq(request), eq(response), eq(chain),
        any(Authentication.class));
  }

  @Test
  @DisplayName("회원정보 존재하지 않으면 UsernameNotFoundException 예외가 발생한다")
  void attempAuthenticationThrowUsernameNotFoundException() {
    String EMAIL = "test@gmail.com";
    String PASSWORD = "1234";
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        EMAIL, PASSWORD);

    String signInInfo = "{ \"email\" : \"" + EMAIL + "\", \"password\" : \"" + PASSWORD + "\" }";
    request.setContent(signInInfo.getBytes());

    when(authenticationManager.authenticate(authenticationToken)).thenThrow(
        new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));

    assertThatThrownBy(
        () -> jwtAuthenticationFilter.attemptAuthentication(request, response)).isInstanceOf(
        UsernameNotFoundException.class).hasMessage("해당하는 유저를 찾을 수 없습니다.");
  }

  @Test
  @DisplayName("회원정보 존재하면 Authentication 객체를 반환한다")
  void attempAuthenticationWithExistUser() {
    String EMAIL = "test@gmail.com";
    String PASSWORD = "1234";
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        EMAIL, PASSWORD);

    String signInInfo = "{ \"email\" : \"" + EMAIL + "\", \"password\" : \"" + PASSWORD + "\" }";
    request.setContent(signInInfo.getBytes());

    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");

    when(authenticationManager.authenticate(authenticationToken)).thenReturn(
        new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD, Collections.singleton(authority)));

    Authentication authentication = jwtAuthenticationFilter.attemptAuthentication(request,
        response);

    assertAll(() -> assertThat(authentication).isNotNull(),
        () -> assertThat(authentication.getPrincipal()).isEqualTo(EMAIL));
  }

  @Test
  @DisplayName("인증 정보가 반환되면 성공적으로 JWT 토큰을 생성하고 쿠키에 추가한다")
  void successfulAuthentication() throws ServletException, IOException {
    String EMAIL = "test@gmail.com";
    String PASSWORD = "1234";
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");

    UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(EMAIL,
        PASSWORD, Collections.singleton(authority));

    jwtAuthenticationFilter.successfulAuthentication(request, response, chain, authResult);

    assertThat(response.getCookies().length).isEqualTo(2);
  }
}