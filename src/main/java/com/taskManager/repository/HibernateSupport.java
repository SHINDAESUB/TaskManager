package com.taskManager.repository;

import org.hibernate.Session;

import javax.persistence.EntityManager;

/**
 * HibernateSupport 는 EntityManager의 Session 인스턴스를 가져온다.
 * EntityManager : 엔티티의 CRUD 작업을 수행한다.
 * Session : DB의 연결을 담당한다.
 */

abstract class HibernateSupport {
  EntityManager entityManager;

  HibernateSupport(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  Session getSession() {
    return entityManager.unwrap(Session.class);
  }

}
