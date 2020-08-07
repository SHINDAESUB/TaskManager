package com.taskManager.web.api;

import java.util.List;

import com.taskManager.domain.application.service.BoardService;
import com.taskManager.domain.application.service.CardListService;
import com.taskManager.domain.application.service.CardService;
import com.taskManager.domain.application.service.TeamService;
import com.taskManager.domain.common.security.CurrentUser;
import com.taskManager.domain.model.board.Board;
import com.taskManager.domain.model.board.BoardId;
import com.taskManager.domain.model.card.Card;
import com.taskManager.domain.model.cardlist.CardList;
import com.taskManager.domain.model.team.Team;
import com.taskManager.domain.model.user.AuthenticatedUser;
import com.taskManager.domain.model.user.User;
import com.taskManager.domain.model.user.UserNotFoundException;
import com.taskManager.web.payload.AddBoardMemberPayload;
import com.taskManager.web.payload.CreateBoardPayload;
import com.taskManager.web.result.ApiResult;
import com.taskManager.web.result.BoardResult;
import com.taskManager.web.result.CreateBoardResult;
import com.taskManager.web.result.Result;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BoardApiController {

  private BoardService boardService;
  private TeamService teamService;
  private CardListService cardListService;
  private CardService cardService;

  public BoardApiController(BoardService boardService,
                            TeamService teamService,
                            CardListService cardListService,
                            CardService cardService) {
    this.boardService = boardService;
    this.teamService = teamService;
    this.cardListService = cardListService;
    this.cardService = cardService;
  }

  @PostMapping("/api/boards")
  public ResponseEntity<ApiResult> createBoard(@RequestBody CreateBoardPayload payload,
                                               @CurrentUser AuthenticatedUser currentUser) {
    Board board = boardService.createBoard(payload.toCommand(currentUser.getUserId()));
    return CreateBoardResult.build(board);
  }

  
  @GetMapping("/api/boards/{boardId}")
  public ResponseEntity<ApiResult> getBoard(@PathVariable("boardId") long rawBoardId) {
    BoardId boardId = new BoardId(rawBoardId);
    Board board = boardService.findById(boardId);
    if (board == null) {
      return Result.notFound();
    }
    List<User> members = boardService.findMembers(boardId);

    Team team = null;
    if (!board.isPersonal()) {
      team = teamService.findById(board.getTeamId());
    }

    List<CardList> cardLists = cardListService.findByBoardId(boardId);
    List<Card> cards = cardService.findByBoardId(boardId);

    return BoardResult.build(team, board, members, cardLists, cards);
  }

  @PostMapping("/api/boards/{boardId}/members")
  public ResponseEntity<ApiResult> addMember(@PathVariable("boardId") long rawBoardId,
                                             @RequestBody AddBoardMemberPayload payload) {
    BoardId boardId = new BoardId(rawBoardId);
    Board board = boardService.findById(boardId);
    if (board == null) {
      return Result.notFound();
    }

    try {
      User member = boardService.addMember(boardId, payload.getUsernameOrEmailAddress());

      ApiResult apiResult = ApiResult.blank()
        .add("id", member.getId().value())
        .add("shortName", member.getInitials());
      return Result.ok(apiResult);
    } catch (UserNotFoundException e) {
      return Result.failure("No user found");
    }
  }
}