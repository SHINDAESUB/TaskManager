package com.taskManager.domain.application.service;

import javax.transaction.Transactional;

import com.taskManager.domain.application.command.CreateBoardCommand;
import com.taskManager.domain.common.event.DomainEventPublisher;
import com.taskManager.domain.model.board.Board;
import com.taskManager.domain.model.board.BoardId;
import com.taskManager.domain.model.board.BoardManagement;
import com.taskManager.domain.model.board.BoardMemberRepository;
import com.taskManager.domain.model.board.BoardRepository;
import com.taskManager.domain.model.board.event.BoardCreatedEvent;
import com.taskManager.domain.model.board.event.BoardMemberAddedEvent;
import com.taskManager.domain.model.user.User;
import com.taskManager.domain.model.user.UserFinder;
import com.taskManager.domain.model.user.UserId;
import com.taskManager.domain.model.user.UserNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {
      
  private BoardMemberRepository boardMemberRepository;
  private UserFinder userFinder;
  private BoardRepository boardRepository;
  private BoardManagement boardManagement;
  private DomainEventPublisher domainEventPublisher;

  public BoardServiceImpl(BoardRepository boardRepository,
                          BoardManagement boardManagement,
                          BoardMemberRepository boardMemberRepository,
                          UserFinder userFinder,
                          DomainEventPublisher domainEventPublisher) {
    this.boardRepository = boardRepository;
    this.boardManagement = boardManagement;
    this.boardMemberRepository = boardMemberRepository;
    this.userFinder = userFinder;
    this.domainEventPublisher = domainEventPublisher;
  }

    @Override
    public List<Board> findBoardsByMembership(UserId userId) {
        return boardRepository.findBoardsByMembership(userId);
    }

    @Override
    public Board findById(BoardId boardId) {
      return boardRepository.findById(boardId);
    }
  
    @Override
    public List<User> findMembers(BoardId boardId) {
      return boardMemberRepository.findMembers(boardId);
    }
  

    @Override
    public Board createBoard(CreateBoardCommand command) {
        Board board = boardManagement.createBoard(command.getUserId(), command.getName(),
        command.getDescription(), command.getTeamId());
      domainEventPublisher.publish(new BoardCreatedEvent(this, board));
      return board;
    }

    
  @Override
  public User addMember(BoardId boardId, String usernameOrEmailAddress) throws UserNotFoundException {
    User user = userFinder.find(usernameOrEmailAddress);
    boardMemberRepository.add(boardId, user.getId());
    domainEventPublisher.publish(new BoardMemberAddedEvent(this, boardId, user));
    return user;
  }
    
    
}