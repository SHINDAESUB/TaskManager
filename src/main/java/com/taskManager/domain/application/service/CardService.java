package com.taskManager.domain.application.service;

import java.util.List;

import com.taskManager.domain.application.command.AddCardCommand;
import com.taskManager.domain.application.command.ChangeCardPositionsCommand;
import com.taskManager.domain.model.board.BoardId;
import com.taskManager.domain.model.card.Card;

public interface CardService {
    
  /**
   * 게시판의 모든 카드를 조회한다.
   */
  List<Card> findByBoardId(BoardId boardId);

  /**
   * 카드를 추가한다.
   */
  Card addCard(AddCardCommand command);

  /**
   * 카드의 위치를 바꾼다.
   */
  void changePositions(ChangeCardPositionsCommand command);
}