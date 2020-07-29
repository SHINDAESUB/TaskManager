package com.taskManager.web.api.authenticate;


import com.taskManager.utils.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** API 요청을 처리함 */
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

  /** "/api/login" 오는 요청은 HTTP POST 처리 */
  public AuthenticationFilter() {
    super(new AntPathRequestMatcher("/api/login", "POST"));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    throws AuthenticationException, IOException {

    log.debug("로그인 요청 프로세스");
    
    /* request.getReader() 요청 바디를 읽어 온다. */
    String requestBody = IOUtils.toString(request.getReader());
    /* JsonUtils로 JSON 문자열로 변환 */
    LoginRequest loginRequest = JsonUtils.toObject(requestBody, LoginRequest.class);
    if (loginRequest == null || loginRequest.isInvalid()) {
      throw new InsufficientAuthenticationException("잘못된 인증 요청"); 
    }
    /* 유효할 경우 token 생성 */
    UsernamePasswordAuthenticationToken token =
      new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password);
    return this.getAuthenticationManager().authenticate(token);
  }

  static class LoginRequest {
    private String username;
    private String password;

    public boolean isInvalid() {
      return StringUtils.isBlank(username) || StringUtils.isBlank(password);
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }
}