package com.taskManager.web.api.authenticate;


import com.taskManager.utils.JsonUtils;

import com.taskManager.web.result.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      ApiResult failure;
      if (exception instanceof BadCredentialsException) {
        failure = ApiResult.message("잘못된 자격 증명");
      } else if (exception instanceof InsufficientAuthenticationException) {
        failure = ApiResult.message("잘못된 인증 요청");
      } else {
        failure = ApiResult.message("인증실패");
      }
      JsonUtils.write(response.getWriter(), failure);
    }
}