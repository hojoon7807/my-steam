package com.hojoon.mysteam.security.jwt;

import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

@ExtendWith(MockitoExtension.class)
class JwtAccessDeniedHanlerTest {

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @InjectMocks
  private JwtAccessDeniedHanler jwtAccessDeniedHanler;

  @Test
  @DisplayName("권한이 없는 사용자가 해당 URI에 접근시 FOBIDDEN 에러를 응답한다")
  void handleFobiddenErrorResponse() throws Exception {
    jwtAccessDeniedHanler.handle(request, response,
        new TestException("refuse access", new RuntimeException()));

    verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
  }

  static class TestException extends AccessDeniedException {

    public TestException(String msg, Throwable cause) {
      super(msg, cause);
    }
  }
}