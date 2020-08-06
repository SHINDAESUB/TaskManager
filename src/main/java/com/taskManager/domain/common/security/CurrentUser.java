package com.taskManager.domain.common.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal

/**
 *  로그인한 사용자의 정보를 가져오기 위한 에노테션 만든다.
 *  스프링 시큐리티의  @AuthenticationPrincipal 을 이용한 어노테이션인 @CurrentUser 만든다. 
 */
public @interface CurrentUser {
}