package com.taskManager.repository;

import javax.persistence.EntityManager;

import com.taskManager.domain.model.board.BoardMember;
import com.taskManager.domain.model.board.BoardMemberRepository;

import org.springframework.stereotype.Repository;

@Repository
public class HibernateBoardMemberRepository extends HibernateSupport<BoardMember> implements BoardMemberRepository {

  HibernateBoardMemberRepository(EntityManager entityManager) {
    super(entityManager);
  }
}