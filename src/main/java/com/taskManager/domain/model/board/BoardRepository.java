package com.taskManager.domain.model.board;

import java.util.List;

import com.taskManager.domain.model.user.UserId;

public interface BoardRepository {
    
    List<Board> findBoardsByMembership(UserId userId);

    void save(Board board);

    /* id를 가지고 게시물을 찾는다. */
    Board findById(BoardId boardId);
}