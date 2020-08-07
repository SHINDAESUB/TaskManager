package com.taskManager.web.payload;

import java.util.List;

import com.taskManager.domain.model.cardlist.CardListPosition;
import com.taskManager.domain.application.command.ChangeCardListPositionsCommand;
import com.taskManager.domain.model.board.BoardId;

public class ChangeCardListPositionsPayload {

  private long boardId;
  private List<CardListPosition> cardListPositions;

  public ChangeCardListPositionsCommand toCommand() {
    return new ChangeCardListPositionsCommand(new BoardId(boardId), cardListPositions);
  }

  public void setBoardId(long boardId) {
    this.boardId = boardId;
  }

  public void setCardListPositions(List<CardListPosition> cardListPositions) {
    this.cardListPositions = cardListPositions;
  }
}