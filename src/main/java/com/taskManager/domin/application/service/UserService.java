package com.taskManager.domin.application.service;

import com.taskManager.domin.application.command.JoinCommand;
import com.taskManager.domin.model.user.exception.join.JoinException;

import org.springframework.security.core.userdetails.UserDetailsService;

/* 스프링 시큐리티 UserDetailsService 상속 */
public interface UserService extends UserDetailsService {
  void join(JoinCommand command) throws JoinException;
}
