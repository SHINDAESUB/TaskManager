package com.taskManager.web.socket;

import io.jsonwebtoken.JwtException;

import com.taskManager.domain.common.security.TokenManager;
import com.taskManager.domain.model.user.UserId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketRequestDispatcher extends TextWebSocketHandler {

  private static final Logger log = LoggerFactory.getLogger(WebSocketRequestDispatcher.class);

  private TokenManager tokenManager;

  public WebSocketRequestDispatcher(TokenManager tokenManager) {
    this.tokenManager = tokenManager;
  }


  /**실시간 토큰 검증해서 인증하는 역할 이다. */
  @Override
  public void afterConnectionEstablished(WebSocketSession webSocketSession) {
    log.debug("WebSocket connection established");
    SocketSession session = new SocketSession(webSocketSession);
    String token = session.getToken();

    try {
      UserId userId = tokenManager.verifyJwt(token);
      session.addAttribute("userId", userId);
      session.reply("인증됨 ");
    } catch (JwtException exception) {
      log.debug("Invalid JWT token value: {}", token);
      session.fail("인증 실패");
    }
  }
}