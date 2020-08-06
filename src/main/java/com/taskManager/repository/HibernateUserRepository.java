package com.taskManager.repository;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import com.taskManager.domain.model.user.User;
import com.taskManager.domain.model.user.UserRepository;

/**
 * 회원가입 구현부
 */
@Repository
public class HibernateUserRepository extends HibernateSupport<User> implements UserRepository {

  HibernateUserRepository(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public User findByUsername(String username) {
    Query <User> query = getSession().createQuery("from User where username = :username", User.class);
    query.setParameter("username", username);
    return query.uniqueResult();
  }

  @Override
  public User findByEmailAddress(String emailAddress) {
    Query<User> query = getSession().createQuery("from User where emailAddress = :emailAddress", User.class);
    query.setParameter("emailAddress", emailAddress);
    return query.uniqueResult();
  }

}
