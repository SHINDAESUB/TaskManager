package com.taskManager.domain.common.security;

import java.security.Key;

import com.taskManager.domain.model.user.UserId;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenManager {
    
    private Key secretKey;
    
    /* application.properties 에 추가되는 비밀 키 (${app.token-secret-key}) 주입한다. */
    public TokenManager(@Value("${app.token-secret-key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
      }
  /**
   * JWT 는 유저 id를 기반으로 JWT 문자열을 생성한다.
   */
  public String jwt(UserId userId) {
    return Jwts.builder()
      .setSubject(String.valueOf(userId.value()))
      .signWith(secretKey).compact();
  }
  
   /**
   * Client 로 부터 받은 토큰을 검증한다.
   */
  public UserId verifyJwt(String jws) {
    String userIdValue = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jws).getBody().getSubject();
    return new UserId(Long.valueOf(userIdValue));
  }
}