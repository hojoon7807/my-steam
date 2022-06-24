package com.hojoon.mysteam.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

  public Cookie createCookie(String cookieName, String value, Long expiry) {
    Cookie cookie = new Cookie(cookieName, value);
    cookie.setHttpOnly(true);
    cookie.setMaxAge((int) (expiry / 1000L));
    cookie.setPath("/");
    return cookie;
  }

  public Cookie getCookie(HttpServletRequest req, String cookieName) {
    Cookie[] cookies = req.getCookies();
    if (cookies == null) {
      return null;
    }
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(cookieName)) {
        return cookie;
      }
    }
    return null;
  }
}
