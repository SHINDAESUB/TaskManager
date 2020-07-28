package com.taskManager.repository;

import org.hibernate.query.Query;
import com.taskManager.domin.model.user.User;
import com.taskManager.domin.model.user.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * 회원가입 구현부
 */
@Repository
public class HibernateUserRepository extends HibernateSupport implements UserRepository {

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

  @Override
  public void save(User newUser) {
    entityManager.persist(newUser);
    entityManager.flush();
  }

}
