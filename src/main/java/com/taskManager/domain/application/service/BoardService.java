package com.taskManager.domain.application.service;

import java.util.List;

import com.taskManager.domain.application.command.CreateBoardCommand;
import com.taskManager.domain.model.board.Board;
import com.taskManager.domain.model.user.UserId;

public interface BoardService {

    /**
     * 로그인한 유저와 연결되있는 게시판의 정보를 가져온다.
     */
    List<Board> findBoardsByMembership(UserId userId);
  
    /**
     * 게시판을 생성
     */
    Board createBoard(CreateBoardCommand command);
  }