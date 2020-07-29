package com.taskManager.mail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.taskManager.domain.common.mail.MessageImpl;

public class MailerTests {

    private JavaMailSender mailSenderMock;
    private MailerImpl instance;

    @Before
    public void setUp() {
      mailSenderMock = mock(JavaMailSender.class);
      instance = new MailerImpl(mailSenderMock);
    }
  
    @Test(expected = IllegalArgumentException.class)
    public void send_nullMessage_shouldFail() {
      instance.send(null);
    }
  
    @Test
    public void send_validMessage_shouldSucceed() {
      String from = "weotjqw@naver.com";
      String to = "weotjqw@gmail.com";
      String subject = "A test message";
      String body = "Username: test, Email Address: weotjqw@gmail.com";
  
      MessageImpl message = new MessageImpl(to, subject, body, from);
      instance.send(message);
  
      SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
      simpleMailMessage.setFrom(from);
      simpleMailMessage.setTo(to);
      simpleMailMessage.setSubject(subject);
      simpleMailMessage.setText("Username: test, Email Address: weotjqw@gmail.com");
      verify(mailSenderMock).send(simpleMailMessage);
    }



}