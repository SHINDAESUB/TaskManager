package com.taskManager.domin.application.service;

import javax.transaction.Transactional;

import com.taskManager.domin.application.command.JoinCommand;
import com.taskManager.domin.common.event.DomainEventPublisher;
import com.taskManager.domin.common.mail.MailManager;
import com.taskManager.domin.common.mail.MessageVariable;
import com.taskManager.domin.model.user.JoinManagement;
import com.taskManager.domin.model.user.User;
import com.taskManager.domin.model.user.event.JoinEvent;
import com.taskManager.domin.model.user.exception.join.JoinException;


import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/* UserService 역할
* 1. model 작업만 조정한다.
* 2. 비즈니스 로직은 수용하지 않는다 (다른 서비스가 비즈니스 로직함 ex) JoinService))
* 3. 도메인 모델에 클라이언트가 접근하는 것을 방지
* 4. 트랜잭션 컨트롤 한다.
*/

@Service
@Transactional
public class UserServiceImpl implements UserService {

  private JoinManagement joinManagement;
  private DomainEventPublisher domainEventPublisher;
  private MailManager mailManager;

  public UserServiceImpl(JoinManagement joinManagement , DomainEventPublisher domainEventPublisher ,MailManager mailManager){
    this.joinManagement = joinManagement;
    this.domainEventPublisher = domainEventPublisher;
    this.mailManager = mailManager;
  }


  @Override
  public void join(JoinCommand command) throws JoinException {

    Assert.notNull(command, "매개변수 command 는 검증을 마치고 'null' 값이 아닙니다.");
    User signUp = joinManagement.join(command.getUsername(), command.getEmailAddress(), command.getPassword());

    sendWelcomeMessage(signUp);
    domainEventPublisher.publish(new JoinEvent(signUp));
  }

  /* 사용자가 등록되면 command에 검증이 완료된 입력한 주소로 보낸다.*/
  private void sendWelcomeMessage(User newUser){
    mailManager.send(newUser.getEmailAddress() , "회원가입을 축하드립니다.", "welcome.ftl", MessageVariable.from("New user", newUser));
  }

}
