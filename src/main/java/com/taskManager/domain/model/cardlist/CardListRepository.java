package com.taskManager.domain.model.cardlist;

import java.util.List;

import com.taskManager.domain.model.board.BoardId;

public interface CardListRepository {

  /**
   * 게시판의 카드 리스트를 불러온다.
   */
  List<CardList> findByBoardId(BoardId boardId);

  /**
   * 카드 리스트를 저장한다.
   */
  void save(CardList cardList);

  /**
   * 카드 리스트의 위치를 변경한다.
   */
  void changePositions(List<CardListPosition> cardListPositions);
}