package com.taskManager.domain.model.card.event;

import com.taskManager.domain.common.event.DomainEvent;
import com.taskManager.domain.model.card.Card;

public class CardAddedEvent extends DomainEvent {

    private static final long serialVersionUID = 26551114425630902L;
  
    private Card card;
  
    public CardAddedEvent(Object source, Card card) {
      super(source);
      this.card = card;
    }
  
    public Card getCard() {
      return card;
    }
}