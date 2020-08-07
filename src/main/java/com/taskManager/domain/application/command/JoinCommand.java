package com.taskManager.domain.application.command;

import org.springframework.util.Assert;

import java.util.Objects;
/* 읽기 전용 */
/* Java Code Generators 확장설치 */
public class JoinCommand {

  private String username;
  private String emailAddress;
  private String password;
  private String firstName;
  private String lastName;

  public JoinCommand(String username, String emailAddress, String firstName, String lastName, String password) {

    Assert.hasText(username, "Parameter `username` 값은 반드시 있어야 합니다.");
    Assert.hasText(emailAddress, "Parameter `emailAddress` 값은 반드시 있어야 합니다.");	    
    Assert.hasText(firstName, "Parameter `firstName` 값은 반드시 있어야 합니다.");
    Assert.hasText(lastName, "Parameter `lastName` 값은 반드시 있어야 합니다.");
    Assert.hasText(password, "Parameter `password` 값은 반드시 있어야 합니다.");	    

    this.username = username;
    this.emailAddress = emailAddress;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
  }

  public String getUsername() {
    return this.username;
  }

  public String getEmailAddress() {
    return this.emailAddress;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPassword() {
    return this.password;
  }

  public JoinCommand() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof JoinCommand)) return false;
    JoinCommand that = (JoinCommand) o;
    if (username != null ? !username.equals(that.username) : that.username != null) return false;
    if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) return false;
    return password != null ? password.equals(that.password) : that.password == null;
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, emailAddress, firstName, lastName, password);
  }

  @Override
  public String toString() {
    return "JoinCommand{" +
      "username='" + username + '\'' +
      ", emailAddress='" + emailAddress + '\'' +
      ", firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", password='" + password + '\'' +
      '}';
  }
}
