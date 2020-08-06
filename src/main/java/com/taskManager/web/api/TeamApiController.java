package com.taskManager.web.api;

import com.taskManager.domain.application.service.TeamService;
import com.taskManager.domain.common.security.CurrentUser;
import com.taskManager.domain.model.team.Team;
import com.taskManager.domain.model.user.AuthenticatedUser;
import com.taskManager.web.payload.CreateTeamPayload;
import com.taskManager.web.result.ApiResult;
import com.taskManager.web.result.CreateTeamResult;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TeamApiController {

  private TeamService teamService;

  public TeamApiController(TeamService teamService) {
    this.teamService = teamService;
  }

  @PostMapping("/api/teams")
  public ResponseEntity<ApiResult> createTeam(@RequestBody CreateTeamPayload payload,
                                              @CurrentUser AuthenticatedUser currentUser) {
    Team team = teamService.createTeam(payload.toCommand(currentUser.getUserId()));
    return CreateTeamResult.build(team);
  }
}