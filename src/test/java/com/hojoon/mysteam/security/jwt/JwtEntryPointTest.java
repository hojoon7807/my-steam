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
import org.springframework.security.core.AuthenticationException;

@ExtendWith(MockitoExtension.class)
class JwtEntryPointTest {

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @InjectMocks
  private JwtEntryPoint jwtEntryPoint;

  @Test
  @DisplayName("인증되지 않은 사용자가 해당 URI에 접근시 UNAUTHORIZED 에러를 응답한다")
  public void responseUnauthorizedError() throws Exception {
    jwtEntryPoint.commence(request, response,
        new TestException("Unauthorized", new RuntimeException()));

    verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }

  static class TestException extends AuthenticationException {

    TestException(String msg, Throwable t) {
      super(msg, t);
    }
  }
}