package com.taskManager.web.api;

import java.util.List;

import com.taskManager.domain.application.service.BoardService;
import com.taskManager.domain.application.service.TeamService;
import com.taskManager.domain.application.service.UserService;
import com.taskManager.domain.common.security.CurrentUser;
import com.taskManager.domain.common.security.TokenManager;
import com.taskManager.domain.model.board.Board;
import com.taskManager.domain.model.team.Team;
import com.taskManager.domain.model.user.AuthenticatedUser;
import com.taskManager.domain.model.user.User;
import com.taskManager.web.result.ApiResult;
import com.taskManager.web.result.MyDataResult;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyInfoApiController {

  private String socketServerUrl;
  private TeamService teamService;
  private BoardService boardService;
  private UserService userService;

  private TokenManager tokenManager;

    /* application.properties 에 추가되는 비밀 키 (${app.socket-server-url}) 주입한다. */
  public MyInfoApiController(@Value("${app.socket-server-url}") 
                             String socketServerUrl,
                             TeamService teamService, 
                             BoardService boardService, 
                             UserService userService, 
                             TokenManager tokenManager) {
    this.teamService = teamService;
    this.boardService = boardService;
    this.userService = userService;
    this.tokenManager =tokenManager;
    this.socketServerUrl = socketServerUrl;
  }

  /**
   *  접속한 사용자가 접근 할 수 있는 모든 게시판과 팀을 가져온다.
   */
  @GetMapping("/api/info")
  public ResponseEntity<ApiResult> getMyData(@CurrentUser AuthenticatedUser currentUser) {
    User user = userService.findById(currentUser.getUserId());
    List<Team> teams = teamService.findTeamsByUserId(currentUser.getUserId());
    List<Board> boards = boardService.findBoardsByMembership(currentUser.getUserId());

    String socketToken = tokenManager.jwt(user.getId());
    return MyDataResult.build(user, teams, boards , socketServerUrl ,socketToken);
  }
}