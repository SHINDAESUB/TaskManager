package com.taskManager.domain.model.card;


import java.util.List;

import com.taskManager.domain.model.board.BoardId;

public interface CardRepository {

  /**
   * 게시판의 Id를 찿는다
   */
  List<Card> findByBoardId(BoardId boardId);

  /**
   * 카드 저장
   */
  void save(Card card);

  /**
   * 카드의 위치 변경
   */
  void changePositions(List<CardPosition> cardPositions);
}