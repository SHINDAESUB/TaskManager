package com.taskManager.domain.application.service;

import java.util.List;

import com.taskManager.domain.application.command.AddCardListCommand;
import com.taskManager.domain.application.command.ChangeCardListPositionsCommand;
import com.taskManager.domain.model.board.BoardId;
import com.taskManager.domain.model.cardlist.CardList;

public interface CardListService {
  /**
   * 게시판의 카드 리스트를 조회한다.
   */
  List<CardList> findByBoardId(BoardId boardId);

  /**
   * 카드 리스트를 추가한다.
   */
  CardList addCardList(AddCardListCommand command);

  /**
   * 카드 리스트의 위치를 변경한다.
   */
  void changePositions(ChangeCardListPositionsCommand command);
}