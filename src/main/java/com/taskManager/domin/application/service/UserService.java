package com.taskManager.domin.application.service;

import com.taskManager.domin.application.command.JoinCommand;
import com.taskManager.domin.model.user.exception.JoinException;

public interface UserService {
  void join(JoinCommand command) throws JoinException;
}
