package com.taskManager.web.payload;

import java.util.List;

import com.taskManager.domain.model.card.CardPosition;
import com.taskManager.domain.application.command.ChangeCardPositionsCommand;
import com.taskManager.domain.model.board.BoardId;

public class ChangeCardPositionsPayload {

  private long boardId;
  private List<CardPosition> cardPositions;

  public ChangeCardPositionsCommand toCommand() {
    return new ChangeCardPositionsCommand(new BoardId(boardId), cardPositions);
  }

  public void setBoardId(long boardId) {
    this.boardId = boardId;
  }

  public void setCardPositions(List<CardPosition> cardPositions) {
    this.cardPositions = cardPositions;
  }
}