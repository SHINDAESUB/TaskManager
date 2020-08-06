package com.taskManager.domain.application.command;

import com.taskManager.domain.model.team.TeamId;
import com.taskManager.domain.model.user.UserId;

public class CreateBoardCommand {

    private UserId userId;
    private String name;
    private String description;
    private TeamId teamId;
  
    public CreateBoardCommand(UserId userId, String name, String description, TeamId teamId) {
      this.userId = userId;
      this.name = name;
      this.description = description;
      this.teamId = teamId;
    }
  
    public UserId getUserId() {
      return userId;
    }
  
    public String getName() {
      return name;
    }
  
    public String getDescription() {
      return description;
    }
  
    public TeamId getTeamId() {
      return teamId;
    }
}