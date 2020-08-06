package com.taskManager.web.result;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taskManager.domain.model.board.Board;
import com.taskManager.domain.model.team.Team;
import com.taskManager.domain.model.user.AuthenticatedUser;

public class MyDataResult {

    public static ResponseEntity<ApiResult> build(AuthenticatedUser currentUser, List<Team> teams, List<Board> boards) {
      Map<String, Object> user = new HashMap<>();
      user.put("name", currentUser.getUsername());
  
      List<TeamResult> teamResults = new ArrayList<>();
      for (Team team : teams) {
        teamResults.add(new TeamResult(team));
      }
  
      List<BoardResult> boardResults = new ArrayList<>();
      for (Board board : boards) {
        boardResults.add(new BoardResult(board));
      }
  
      ApiResult apiResult = ApiResult.blank()
        .add("user", user)
        .add("teams", teamResults)
        .add("boards", boardResults);
  
      return Result.ok(apiResult);
    }
  
    private static class TeamResult {
      private long id;
      private String name;
  
      TeamResult(Team team) {
        this.id = team.getId().value();
        this.name = team.getName();
      }
  
      public long getId() {
        return id;
      }
  
      public String getName() {
        return name;
      }
    }
  
    private static class BoardResult {
      private long id;
      private String name;
      private String description;
      private long teamId;
  
      BoardResult(Board board) {
        this.id = board.getId().value();
        this.name = board.getName();
        this.description = board.getDescription();
        this.teamId = board.getTeamId().value();
      }
  
      public long getId() {
        return id;
      }
  
      public String getName() {
        return name;
      }
  
      public String getDescription() {
        return description;
      }
  
      public long getTeamId() {
        return teamId;
      }
    }
  }   