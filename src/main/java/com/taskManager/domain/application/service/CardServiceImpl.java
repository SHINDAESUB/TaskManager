package com.taskManager.domain.application.service;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import com.taskManager.domain.application.command.AddCardCommand;
import com.taskManager.domain.application.command.ChangeCardPositionsCommand;
import com.taskManager.domain.common.event.DomainEventPublisher;
import com.taskManager.domain.model.board.BoardId;
import com.taskManager.domain.model.card.Card;
import com.taskManager.domain.model.card.CardRepository;
import com.taskManager.domain.model.card.event.CardAddedEvent;

import java.util.List;

@Service
@Transactional
public class CardServiceImpl implements CardService {

  private CardRepository cardRepository;
  private DomainEventPublisher domainEventPublisher;

  public CardServiceImpl(CardRepository cardRepository,
                         DomainEventPublisher domainEventPublisher) {
    this.cardRepository = cardRepository;
    this.domainEventPublisher = domainEventPublisher;
  }

  @Override
  public List<Card> findByBoardId(BoardId boardId) {
    return cardRepository.findByBoardId(boardId);
  }

  @Override
  public Card addCard(AddCardCommand command) {
    Card card = Card.create(command.getCardListId(), command.getUserId(), command.getTitle(), command.getPosition());
    cardRepository.save(card);
    domainEventPublisher.publish(new CardAddedEvent(this, card));
    return card;
  }

  @Override
  public void changePositions(ChangeCardPositionsCommand command) {
    cardRepository.changePositions(command.getCardPositions());
  }
}