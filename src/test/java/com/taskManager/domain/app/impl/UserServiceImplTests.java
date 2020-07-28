package com.taskManager.domain.app.impl;

import com.taskManager.domin.application.command.JoinCommand;
import com.taskManager.domin.application.service.UserServiceImpl;
import com.taskManager.domin.common.event.DomainEventPublisher;
import com.taskManager.domin.common.mail.MailManager;
import com.taskManager.domin.common.mail.MessageVariable;
import com.taskManager.domin.model.user.JoinManagement;
import com.taskManager.domin.model.user.User;
import com.taskManager.domin.model.user.event.JoinEvent;
import com.taskManager.domin.model.user.exception.join.EmailAddressExistsException;
import com.taskManager.domin.model.user.exception.join.JoinException;
import com.taskManager.domin.model.user.exception.join.UsernameExistsException;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class UserServiceImplTests {
    
    private JoinManagement joinManagementMock;
    private DomainEventPublisher domainEventPublisherMock;
    private MailManager mailManagerMock;
    private UserServiceImpl instance;

    @Before
    public void setUp() {
        joinManagementMock = mock(JoinManagement.class);
        domainEventPublisherMock = mock(DomainEventPublisher.class);
      mailManagerMock = mock(MailManager.class);
      instance = new UserServiceImpl(joinManagementMock, domainEventPublisherMock, mailManagerMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void join_nullCommand_shouldFail() throws JoinException {
      instance.join(null);
    }
  
    @Test(expected = JoinException.class)
    public void join_existingUsername_shouldFail() throws JoinException {
      String username = "existing";
      String emailAddress = "sunny@taskagile.com";
      String password = "MyPassword!";
      doThrow(UsernameExistsException.class).when(joinManagementMock)
        .join(username, emailAddress, password);
  
      JoinCommand command = new JoinCommand(username, emailAddress, password);
      instance.join(command);
    }
  
    @Test(expected = JoinException.class)
    public void join_existingEmailAddress_shouldFail() throws JoinException {
      String username = "sunny";
      String emailAddress = "existing@taskagile.com";
      String password = "MyPassword!";
      doThrow(EmailAddressExistsException.class).when(joinManagementMock)
        .join(username, emailAddress, password);
  
        JoinCommand command = new JoinCommand(username, emailAddress, password);
      instance.join(command);
    }
  
  
    @Test
    public void join_validCommand_shouldSucceed() throws JoinException {
      String username = "sunny";
      String emailAddress = "sunny@taskagile.com";
      String password = "MyPassword!";
      User newUser = User.create(username, emailAddress, password);
      when(joinManagementMock.join(username, emailAddress, password))
        .thenReturn(newUser);
        JoinCommand command = new JoinCommand(username, emailAddress, password);
  
      instance.join(command);
  
    /* error : UserServiceImpl.java 메시지와 일치하는지 확인  */ 
      verify(mailManagerMock).send(
        emailAddress,
        "회원가입을 축하드립니다.",
        "welcome.ftl",
        MessageVariable.from("New user", newUser)
      );
      verify(domainEventPublisherMock).publish(new JoinEvent(newUser));
    }    
}