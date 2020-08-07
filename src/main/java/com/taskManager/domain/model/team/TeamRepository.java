package com.taskManager.domain.model.team;

import java.util.List;

import com.taskManager.domain.model.user.UserId;

public interface TeamRepository {
    
    List<Team> findTeamsByUserId(UserId userId);

  /**
   * Find a team by id
   */
    Team findById(TeamId teamId);

    void save(Team team);
}