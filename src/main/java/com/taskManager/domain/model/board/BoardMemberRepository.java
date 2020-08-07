package com.taskManager.domain.model.board;

import java.util.List;

import com.taskManager.domain.model.user.User;
import com.taskManager.domain.model.user.UserId;

public interface BoardMemberRepository {
    
    /* 게시판의 맴버를 찾는다 */
    List<User> findMembers(BoardId boardId);

    void save(BoardMember boardMember);

    /* 게시판의 유저를 추가한다. */
    void add(BoardId boardId, UserId userId);


}