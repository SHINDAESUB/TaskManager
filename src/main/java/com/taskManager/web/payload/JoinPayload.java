package com.taskManager.web.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.taskManager.domain.application.command.JoinCommand;

/* @Size 어노테이션은 null 값을 유효값으로 간주하기 떄문에 @NotNull 적어주세요 */
public class JoinPayload {

  @Size(min = 2 ,max = 50, message = "Id는 적어도 2 에서 50 사이로 입력해주세요")
  @NotNull
  private String username;

  @Email(message = "이메일 정확한 입력값이 필요")
  @Size(max = 100 , message = "에메일은 100자 안으로 입력해주세요")
  @NotNull
  private String emailAddress;

  @Size(min = 1, max = 45, message = "성은 적어도 1 이상 45 이하 문자 입력 해주세요")
  @NotNull
  private String firstName;

  @Size(min = 1, max = 45, message = "이름은 적어도 1 이상 45 이하 문자 입력 해주세요")
  @NotNull
  private String lastName;


  @Size(min= 6 ,max = 30 ,message = "비밀번호는 6 에서 30 입력해주세요")
  @NotNull
  private String password;

  //검증에 문제가 없으면 JoinPayload 인스턴스를 가지고 JoinCommand를 생성한다.
  public JoinCommand toCommand() {
    return new JoinCommand(this.username, this.emailAddress, this.firstName, this.lastName, this.password);
  }


  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmailAddress() {
    return this.emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }


  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }



}
