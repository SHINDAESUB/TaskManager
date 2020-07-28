package com.taskManager.domin.common.mail;


public interface MailManager {

  /*
  *  @param emailAddress 받는 사람의 이메일 주소
  *  @param subject 제목
  *  @param template 메일의 템플릿
  *  @param MessageVariable 메일의 템플릿의 변수
  */
  void send (String emailAddress , String  subject , String template , MessageVariable... variables);

}
