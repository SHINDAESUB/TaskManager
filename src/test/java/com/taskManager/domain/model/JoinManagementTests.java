package com.taskManager.domain.model;

import com.taskManager.domin.common.security.PasswordEncryptor;
import com.taskManager.domin.model.user.JoinManagement;
import com.taskManager.domin.model.user.User;
import com.taskManager.domin.model.user.UserRepository;
import com.taskManager.domin.model.user.exception.join.EmailAddressExistsException;
import com.taskManager.domin.model.user.exception.join.JoinException;
import com.taskManager.domin.model.user.exception.join.UsernameExistsException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class JoinManagementTests {
    private UserRepository repositoryMock;
    private PasswordEncryptor passwordEncryptorMock;
    private JoinManagement instance;
  
    @Before
    public void setUp() {
      repositoryMock = mock(UserRepository.class);
      passwordEncryptorMock = mock(PasswordEncryptor.class);
      instance = new JoinManagement(repositoryMock, passwordEncryptorMock);
    }
  
    @Test(expected = UsernameExistsException.class)
    public void join_existedUsername_shouldFail() throws JoinException {
      String username = "existUsername";
      String emailAddress = "sunny@taskagile.com";
      String password = "MyPassword!";
      // We just return an empty user object to indicate an existing user
      when(repositoryMock.findByUsername(username)).thenReturn(new User());
      instance.join(username, emailAddress, password);
    }
  
    @Test(expected = EmailAddressExistsException.class)
    public void join_existedEmailAddress_shouldFail() throws JoinException {
      String username = "sunny";
      String emailAddress = "exist@taskagile.com";
      String password = "MyPassword!";
      // We just return an empty user object to indicate an existing user
      when(repositoryMock.findByEmailAddress(emailAddress)).thenReturn(new User());
      instance.join(username, emailAddress, password);
    }
  
    @Test
    public void join_uppercaseEmailAddress_shouldSucceedAndBecomeLowercase() throws JoinException {
      String username = "sunny";
      String emailAddress = "Sunny@TaskAgile.com";
      String password = "MyPassword!";
      instance.join(username, emailAddress, password);
      User userToSave = User.create(username, emailAddress.toLowerCase(), password);
      verify(repositoryMock).save(userToSave);
    }
  
    @Test
    public void join_newUser_shouldSucceed() throws JoinException {
      String username = "sunny";
      String emailAddress = "sunny@taskagile.com";
      String password = "MyPassword!";
      String encryptedPassword = "EncryptedPassword";
      User newUser = User.create(username, emailAddress, encryptedPassword);
  
      // Setup repository mock
      // Return null to indicate no user exists
      when(repositoryMock.findByUsername(username)).thenReturn(null);
      when(repositoryMock.findByEmailAddress(emailAddress)).thenReturn(null);
      doNothing().when(repositoryMock).save(newUser);
      // Setup passwordEncryptor mock
      when(passwordEncryptorMock.encrypt(password)).thenReturn("EncryptedPassword");
  
      User savedUser = instance.join(username, emailAddress, password);
      InOrder inOrder = inOrder(repositoryMock);
      inOrder.verify(repositoryMock).findByUsername(username);
      inOrder.verify(repositoryMock).findByEmailAddress(emailAddress);
      inOrder.verify(repositoryMock).save(newUser);
      verify(passwordEncryptorMock).encrypt(password);
      assertEquals("Saved user's password should be encrypted", encryptedPassword, savedUser.getPassword());
    }
}