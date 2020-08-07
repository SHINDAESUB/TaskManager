package com.taskManager.domain.application.service;

import com.taskManager.domain.application.command.JoinCommand;
import com.taskManager.domain.model.user.User;
import com.taskManager.domain.model.user.UserId;
import com.taskManager.domain.model.user.exception.join.JoinException;

import org.springframework.security.core.userdetails.UserDetailsService;

/* 스프링 시큐리티 UserDetailsService 상속 */
public interface UserService extends UserDetailsService {

  /**
   * 유저의 아이디를 찾는다.
   */
  User findById(UserId userId);
  
  void join(JoinCommand command) throws JoinException;
}
