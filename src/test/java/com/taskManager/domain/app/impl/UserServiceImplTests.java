package com.taskManager.domain.app.impl;

import org.junit.Before;
import org.junit.Test;
import org.apache.commons.lang3.reflect.FieldUtils;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.taskManager.domain.application.command.JoinCommand;
import com.taskManager.domain.application.service.UserServiceImpl;
import com.taskManager.domain.common.event.DomainEventPublisher;
import com.taskManager.domain.common.mail.MailManager;
import com.taskManager.domain.common.mail.MessageVariable;
import com.taskManager.domain.model.user.AuthenticatedUser;
import com.taskManager.domain.model.user.JoinManagement;
import com.taskManager.domain.model.user.User;
import com.taskManager.domain.model.user.UserRepository;
import com.taskManager.domain.model.user.event.JoinEvent;
import com.taskManager.domain.model.user.exception.join.EmailAddressExistsException;
import com.taskManager.domain.model.user.exception.join.JoinException;
import com.taskManager.domain.model.user.exception.join.UsernameExistsException;

public class UserServiceImplTests {
    
    private JoinManagement joinManagementMock;
    private DomainEventPublisher domainEventPublisherMock;
    private MailManager mailManagerMock;
    private UserRepository userRepositoryMock;
    private UserServiceImpl instance;

    @Before
    public void setUp() {
        joinManagementMock = mock(JoinManagement.class);
        domainEventPublisherMock = mock(DomainEventPublisher.class);
        mailManagerMock = mock(MailManager.class);
        userRepositoryMock = mock(UserRepository.class);
      instance = new UserServiceImpl(joinManagementMock, domainEventPublisherMock, mailManagerMock,userRepositoryMock);
    }

    @Test
    public void loadUserByUsername_emptyUsername_shouldFail() {
      Exception exception = null;
      try {
        instance.loadUserByUsername("");
      } catch (Exception e) {
        exception = e;
      }
      assertNotNull(exception);
      assertTrue(exception instanceof UsernameNotFoundException);
      verify(userRepositoryMock, never()).findByUsername("");
      verify(userRepositoryMock, never()).findByEmailAddress("");
    }
    @Test
    public void loadUserByUsername_notExistUsername_shouldFail() {
      String notExistUsername = "test";
      when(userRepositoryMock.findByUsername(notExistUsername)).thenReturn(null);
      Exception exception = null;
      try {
        instance.loadUserByUsername(notExistUsername);
      } catch (Exception e) {
        exception = e;
      }
      assertNotNull(exception);
      assertTrue(exception instanceof UsernameNotFoundException);
      verify(userRepositoryMock).findByUsername(notExistUsername);
      verify(userRepositoryMock, never()).findByEmailAddress(notExistUsername);
    }
    @Test
    public void loadUserByUsername_existUsername_shouldSucceed() throws IllegalAccessException {
      String existUsername = "test";
      User foundUser = User.create(existUsername, "test@test.com", "EncryptedPassword!");
      foundUser.updateName("Test", "User");

      FieldUtils.writeField(foundUser, "id", 1L, true);
      when(userRepositoryMock.findByUsername(existUsername)).thenReturn(foundUser);
      Exception exception = null;
      UserDetails userDetails = null;
      try {
        userDetails = instance.loadUserByUsername(existUsername);
      } catch (Exception e) {
        exception = e;
      }
      assertNull(exception);
      verify(userRepositoryMock).findByUsername(existUsername);
      verify(userRepositoryMock, never()).findByEmailAddress(existUsername);
      assertNotNull(userDetails);
      assertEquals(existUsername, userDetails.getUsername());
      assertTrue(userDetails instanceof AuthenticatedUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void join_nullCommand_shouldFail() throws JoinException {
      instance.join(null);
    }
  
    @Test(expected = JoinException.class)
    public void join_existingUsername_shouldFail() throws JoinException {
      String username = "existing";
      String emailAddress = "tests@test.com";
      String password = "MyPassword!";
      doThrow(UsernameExistsException.class).when(joinManagementMock)
        .join(username, emailAddress, password);
  
      JoinCommand command = new JoinCommand(username, emailAddress, password);
      instance.join(command);
    }
  
    @Test(expected = JoinException.class)
    public void join_existingEmailAddress_shouldFail() throws JoinException {
      String username = "tests";
      String emailAddress = "existing@test.com";
      String password = "MyPassword!";
      doThrow(EmailAddressExistsException.class).when(joinManagementMock)
        .join(username, emailAddress, password);
  
        JoinCommand command = new JoinCommand(username, emailAddress, password);
      instance.join(command);
    }
  
  
    @Test
    public void join_validCommand_shouldSucceed() throws JoinException {
      String username = "tests";
      String emailAddress = "tests@test.com";
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
      verify(domainEventPublisherMock).publish(new JoinEvent( this,newUser));
    }    
}