package com.taskManager.domain.model.board;

import com.taskManager.domain.model.team.TeamId;
import com.taskManager.domain.model.user.UserId;

import org.springframework.stereotype.Component;

/* 게시판 비즈니스 로직 */

@Component
public class BoardManagement {

  private BoardRepository boardRepository;
  private BoardMemberRepository boardMemberRepository;

  public BoardManagement(BoardRepository boardRepository, 
                         BoardMemberRepository boardMemberRepository) {
    this.boardRepository = boardRepository;
    this.boardMemberRepository = boardMemberRepository;
  }

  /**
   * 새 게시물 생성
   *
   * @param creatorId the user id who creates this board
   * @param name the name of the board
   * @param description the description of the board
   * @param teamId 속해는 팀이 있으면 넣어주고 없으면 개인 게시물일 경우엔 'null' 값을 넣어준다
   */
  public Board createBoard(UserId creatorId, String name, String description, TeamId teamId) {
    Board board = Board.create(creatorId, name, description, teamId);
    boardRepository.save(board);
    
    /* 생성자를 게시판의 멤버로 추가 */
    BoardMember boardMember = BoardMember.create(board.getId(), creatorId);
    boardMemberRepository.save(boardMember);
    return board;
  }   
}