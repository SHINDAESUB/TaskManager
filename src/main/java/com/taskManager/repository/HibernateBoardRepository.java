package com.taskManager.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.taskManager.domain.model.board.Board;
import com.taskManager.domain.model.board.BoardRepository;
import com.taskManager.domain.model.user.UserId;

import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateBoardRepository extends HibernateSupport<Board> implements BoardRepository {

  HibernateBoardRepository(EntityManager entityManager) {
    super(entityManager);
  }

  /**
   *  board 테이블 과 board_mamber 테이블을 조인한다.
   *  원래는 @ManyToMany 활용해야 하지만 하지 않았기 떄문에 LEFT JOIN 한다.
   *  
   */
  @Override
  public List<Board> findBoardsByMembership(UserId userId) {
    String sql = "SELECT b.* FROM board b LEFT JOIN board_member bm ON b.id = bm.board_id WHERE bm.user_id = :userId";
    NativeQuery<Board> query = getSession().createNativeQuery(sql, Board.class);
    query.setParameter("userId", userId.value());
    return query.list();
  }

}