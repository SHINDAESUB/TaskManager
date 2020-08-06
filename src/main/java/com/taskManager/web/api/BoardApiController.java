package com.taskManager.web.api;

import com.taskManager.domain.application.service.BoardService;
import com.taskManager.domain.common.security.CurrentUser;
import com.taskManager.domain.model.board.Board;
import com.taskManager.domain.model.user.AuthenticatedUser;
import com.taskManager.web.payload.CreateBoardPayload;
import com.taskManager.web.result.ApiResult;
import com.taskManager.web.result.CreateBoardResult;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BoardApiController {

  private BoardService boardService;

  public BoardApiController(BoardService boardService) {
    this.boardService = boardService;
  }

  @PostMapping("/api/boards")
  public ResponseEntity<ApiResult> createBoard(@RequestBody CreateBoardPayload payload,
                                               @CurrentUser AuthenticatedUser currentUser) {
    Board board = boardService.createBoard(payload.toCommand(currentUser.getUserId()));
    return CreateBoardResult.build(board);
  }
}