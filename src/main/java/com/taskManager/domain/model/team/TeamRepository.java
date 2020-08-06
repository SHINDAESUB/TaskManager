package com.taskManager.domain.model.team;

import java.util.List;

import com.taskManager.domain.model.user.UserId;

public interface TeamRepository {
    
    List<Team> findTeamsByUserId(UserId userId);

    void save(Team team);
}