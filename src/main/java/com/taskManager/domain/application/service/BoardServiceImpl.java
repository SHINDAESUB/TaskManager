package com.taskManager.domain.application.service;

import javax.transaction.Transactional;

import com.taskManager.domain.application.command.CreateBoardCommand;
import com.taskManager.domain.common.event.DomainEventPublisher;
import com.taskManager.domain.model.board.Board;
import com.taskManager.domain.model.board.BoardManagement;
import com.taskManager.domain.model.board.BoardRepository;
import com.taskManager.domain.model.board.event.BoardCreatedEvent;
import com.taskManager.domain.model.user.UserId;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {
    	
  private BoardRepository boardRepository;
  private BoardManagement boardManagement;
  private DomainEventPublisher domainEventPublisher;

  public BoardServiceImpl(BoardRepository boardRepository,
                          BoardManagement boardManagement,
                          DomainEventPublisher domainEventPublisher) {
    this.boardRepository = boardRepository;
    this.boardManagement = boardManagement;
    this.domainEventPublisher = domainEventPublisher;
  }

    @Override
    public List<Board> findBoardsByMembership(UserId userId) {
        return boardRepository.findBoardsByMembership(userId);
    }

    @Override
    public Board createBoard(CreateBoardCommand command) {
        Board board = boardManagement.createBoard(command.getUserId(), command.getName(),
        command.getDescription(), command.getTeamId());
      domainEventPublisher.publish(new BoardCreatedEvent(this, board));
      return board;
    }
    
    
}