package com.taskManager.domain.model.user;

/**
 * UserRepository 구현
 * 1.UserRepository 그대로 유지
 * 2.HibernateUserRepository 구현
 * 3.JPA 커넥션 풀 , 데이터 소스 인스턴스, 엔티티 매니저 인터페이스 생성
 */
public interface UserRepository {

  User findByUsername(String username);

  User findByEmailAddress(String emailAddress);
  
  void save(User user);
}
