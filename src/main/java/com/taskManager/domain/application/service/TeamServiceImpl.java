package com.taskManager.domain.application.service;


import javax.transaction.Transactional;

import com.taskManager.domain.application.command.CreateTeamCommand;
import com.taskManager.domain.common.event.DomainEventPublisher;
import com.taskManager.domain.model.team.Team;
import com.taskManager.domain.model.team.TeamId;
import com.taskManager.domain.model.team.TeamRepository;
import com.taskManager.domain.model.team.event.TeamCreatedEvent;
import com.taskManager.domain.model.user.UserId;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

  private TeamRepository teamRepository;
  private DomainEventPublisher domainEventPublisher;

  public TeamServiceImpl(TeamRepository teamRepository, DomainEventPublisher domainEventPublisher) {
    this.teamRepository = teamRepository;
    this.domainEventPublisher = domainEventPublisher;
  }

  @Override
  public List<Team> findTeamsByUserId(UserId userId) {
    return teamRepository.findTeamsByUserId(userId);
  }
  
  @Override
  public Team findById(TeamId teamId) {
    return teamRepository.findById(teamId);
  }

  @Override
  public Team createTeam(CreateTeamCommand command) {
    Team team = Team.create(command.getName(), command.getUserId());
    teamRepository.save(team);
    domainEventPublisher.publish(new TeamCreatedEvent(this, team));
    return team;
  }
}