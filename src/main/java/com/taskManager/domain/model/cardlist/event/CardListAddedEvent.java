package com.taskManager.domain.model.cardlist.event;

import com.taskManager.domain.common.event.DomainEvent;
import com.taskManager.domain.model.cardlist.CardList;

public class CardListAddedEvent extends DomainEvent {

    private static final long serialVersionUID = -877934435476435188L;
  
    private CardList cardList;
  
    public CardListAddedEvent(Object source, CardList cardList) {
      super(source);
      this.cardList = cardList;
    }
  
    public CardList getCardList() {
      return cardList;
    }
}