package com.hojoon.mysteam.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class CookieUtilTest {
  private CookieUtil cookieUtil;

  @BeforeEach
  void init(){
    cookieUtil = new CookieUtil();
  }

  @Test
  @DisplayName("주어진 값으로 성공적으로 쿠키를 생성한다")
  void createCookie(){
    String NAME = "cookie";

    Cookie cookie = cookieUtil.createCookie(NAME, "value", 1000L);

    assertAll(
        () -> assertThat(cookie).isNotNull(),
        () -> assertThat(cookie.getName()).isEqualTo(NAME)
    );
  }

  @Test
  @DisplayName("요청에 쿠키가 존재하지 않으면 null을 반환한다")
  void getCookieWithNotExistCookieRequest(){
    MockHttpServletRequest request = new MockHttpServletRequest();

    Cookie cookie = cookieUtil.getCookie(request, "cookie");

    assertThat(cookie).isNull();
  }

  @Test
  @DisplayName("해당 이름의 쿠키가 존재하지 않으면 null을 반환한다")
  void getCookieWithNotExistNameCookieRequest(){
    MockHttpServletRequest request = new MockHttpServletRequest();
    String NAME = "test";
    request.setCookies(new Cookie("cookie", "value"));

    Cookie cookie = cookieUtil.getCookie(request, NAME);

    assertThat(cookie).isNull();
  }

  @Test
  @DisplayName("해당 이름의 쿠키가 존재하면 해당 쿠키를 반환한다")
  void getCookieWithExistNameCookieRequest(){
    MockHttpServletRequest request = new MockHttpServletRequest();
    String NAME = "test";
    request.setCookies(new Cookie(NAME, "value"));

    Cookie cookie = cookieUtil.getCookie(request, NAME);

    assertAll(
        () -> assertThat(cookie).isNotNull(),
        () -> assertThat(cookie.getName()).isEqualTo(NAME)
    );
  }
}