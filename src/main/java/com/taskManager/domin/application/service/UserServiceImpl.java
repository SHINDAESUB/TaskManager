package com.taskManager.domin.application.service;

import com.taskManager.domin.application.command.JoinCommand;
import com.taskManager.domin.model.user.exception.JoinException;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Override
  public void join(JoinCommand command) throws JoinException {

  }

}
