package com.taskManager.web.api;

import java.util.List;

import com.taskManager.domain.application.service.BoardService;
import com.taskManager.domain.application.service.TeamService;
import com.taskManager.domain.application.service.UserService;
import com.taskManager.domain.common.security.CurrentUser;
import com.taskManager.domain.model.board.Board;
import com.taskManager.domain.model.team.Team;
import com.taskManager.domain.model.user.AuthenticatedUser;
import com.taskManager.domain.model.user.User;
import com.taskManager.web.result.ApiResult;
import com.taskManager.web.result.MyDataResult;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyInfoApiController {

  private TeamService teamService;
  private BoardService boardService;
  private UserService userService;

  public MyInfoApiController(TeamService teamService, BoardService boardService, UserService userService) {
    this.teamService = teamService;
    this.boardService = boardService;
    this.userService = userService;
  }

  /**
   *  접속한 사용자가 접근 할 수 있는 모든 게시판과 팀을 가져온다.
   */
  @GetMapping("/api/info")
  public ResponseEntity<ApiResult> getMyData(@CurrentUser AuthenticatedUser currentUser) {
    User user = userService.findById(currentUser.getUserId());
    List<Team> teams = teamService.findTeamsByUserId(currentUser.getUserId());
    List<Board> boards = boardService.findBoardsByMembership(currentUser.getUserId());
    return MyDataResult.build(user, teams, boards);
  }
}