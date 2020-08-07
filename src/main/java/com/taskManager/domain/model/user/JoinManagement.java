package com.taskManager.domain.model.user;


import com.taskManager.domain.model.user.exception.join.EmailAddressExistsException;
import com.taskManager.domain.model.user.exception.join.JoinException;
import com.taskManager.domain.model.user.exception.join.UsernameExistsException;
import com.taskManager.domain.common.security.PasswordEncryptor;
import org.springframework.stereotype.Component;

/* 회원가입 비즈니스 로직 */
@Component
public class JoinManagement {

  private UserRepository repository;
  private PasswordEncryptor passwordEncryptor;

  public JoinManagement(UserRepository repository, PasswordEncryptor passwordEncryptor) {
    this.repository = repository;
    this.passwordEncryptor = passwordEncryptor;
  }

  public User join(String username, String emailAddress,String firstName, String lastName, String password) throws JoinException {
    User existingUser = repository.findByUsername(username); //유저 이름 중복 조회(존재할 경우 예외처리 / 없을 경우 null 값)
      if (existingUser != null) {
        throw new UsernameExistsException();
      }

    existingUser = repository.findByEmailAddress(emailAddress.toLowerCase());  //이메일 중복 조회(존재할 경우 예외처리 / 없을 경우 null 값)
      if (existingUser != null) {
        throw new EmailAddressExistsException();
      }

    String encryptedPassword = passwordEncryptor.encrypt(password); //비밀번호 복호화
    User signUp = User.create(username, emailAddress.toLowerCase(),firstName, lastName,  encryptedPassword);
    repository.save(signUp);
    return signUp;
  }
}
