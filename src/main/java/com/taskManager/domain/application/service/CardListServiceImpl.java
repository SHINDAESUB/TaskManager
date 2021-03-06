package com.taskManager.domain.application.service;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import com.taskManager.domain.application.command.AddCardListCommand;
import com.taskManager.domain.application.command.ChangeCardListPositionsCommand;
import com.taskManager.domain.common.event.DomainEventPublisher;
import com.taskManager.domain.model.board.BoardId;
import com.taskManager.domain.model.cardlist.CardList;
import com.taskManager.domain.model.cardlist.CardListRepository;
import com.taskManager.domain.model.cardlist.event.CardListAddedEvent;

import java.util.List;

@Service
@Transactional
public class CardListServiceImpl implements CardListService {

  private CardListRepository cardListRepository;
  private DomainEventPublisher domainEventPublisher;

  public CardListServiceImpl(CardListRepository cardListRepository,
                             DomainEventPublisher domainEventPublisher) {
    this.cardListRepository = cardListRepository;
    this.domainEventPublisher = domainEventPublisher;
  }

  @Override
  public List<CardList> findByBoardId(BoardId boardId) {
    return cardListRepository.findByBoardId(boardId);
  }

  @Override
  public CardList addCardList(AddCardListCommand command) {
    CardList cardList = CardList.create(command.getBoardId(),
      command.getUserId(), command.getName(), command.getPosition());

    cardListRepository.save(cardList);
    domainEventPublisher.publish(new CardListAddedEvent(this, cardList));
    return cardList;
  }

  @Override
  public void changePositions(ChangeCardListPositionsCommand command) {
    cardListRepository.changePositions(command.getCardListPositions());
  }
}