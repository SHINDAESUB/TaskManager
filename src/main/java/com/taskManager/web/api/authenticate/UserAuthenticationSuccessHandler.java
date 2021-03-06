package com.taskManager.web.api.authenticate;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taskManager.utils.JsonUtils;
import com.taskManager.web.result.ApiResult;

import java.io.IOException;
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
      response.setStatus(HttpStatus.OK.value());
      JsonUtils.write(response.getWriter(), ApiResult.message("인증 확인"));
    }
}