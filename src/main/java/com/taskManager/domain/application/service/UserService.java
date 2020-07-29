package com.taskManager.domain.application.service;

import com.taskManager.domain.application.command.JoinCommand;
import com.taskManager.domain.model.user.exception.join.JoinException;

import org.springframework.security.core.userdetails.UserDetailsService;

/* 스프링 시큐리티 UserDetailsService 상속 */
public interface UserService extends UserDetailsService {
  void join(JoinCommand command) throws JoinException;
}
