package com.taskManager.domain.application.service;

import java.util.List;

import com.taskManager.domain.application.command.CreateTeamCommand;
import com.taskManager.domain.model.team.Team;
import com.taskManager.domain.model.team.TeamId;
import com.taskManager.domain.model.user.UserId;

public interface TeamService {

  /**
   * 유저가 참여한 팀을 조회한다.
   */
  List<Team> findTeamsByUserId(UserId userId);
  
  /**
   * Id 를 가지고 팀을 찾느다.
   */
  Team findById(TeamId teamId);

  /**
   * 팀 생성
   */
  Team createTeam(CreateTeamCommand command);
}