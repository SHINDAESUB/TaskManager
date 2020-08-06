package com.taskManager.web.payload;

import com.taskManager.domain.application.command.CreateTeamCommand;
import com.taskManager.domain.model.user.UserId;

public class CreateTeamPayload {

    private String name;
  
    public CreateTeamCommand toCommand(UserId userId) {
      return new CreateTeamCommand(userId, name);
    }
  
    public void setName(String name) {
      this.name = name;
    }
  }