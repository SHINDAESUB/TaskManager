package com.taskManager.domain.application.service;

import java.util.List;

import com.taskManager.domain.application.command.CreateBoardCommand;
import com.taskManager.domain.model.board.Board;
import com.taskManager.domain.model.board.BoardId;
import com.taskManager.domain.model.user.User;
import com.taskManager.domain.model.user.UserId;
import com.taskManager.domain.model.user.UserNotFoundException;

public interface BoardService {

    /**
     * 로그인한 유저와 연결되있는 게시판의 정보를 가져온다.
     */
    List<Board> findBoardsByMembership(UserId userId);

    /**
     * Id를 가지고 게시판 ID 찾는다.
     */
    Board findById(BoardId boardId);

    /**
     * 게시판에 맴버정보를 찾는다.
     */
    List<User> findMembers(BoardId boardId);


    /**
     * 게시판을 생성
     */
    Board createBoard(CreateBoardCommand command);

    
  /**
   * 게시판에 맴버를 추가한다.
   */
  User addMember(BoardId boardId, String usernameOrEmailAddress) throws UserNotFoundException;
  }