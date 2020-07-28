package com.taskManager.domin.model.user.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class JoinEventHandler {

  private final static Logger log = LoggerFactory.getLogger(JoinEventHandler.class);

  /* 메소드 바디는 단순히 디버그 정보를 로그로 출력 */
  @EventListener(JoinEvent.class)
  public void handleEvent(JoinEvent event){
    log.debug("핸들링 `{}` 회원가입 이벤트", event.getUser().getEmailAddress());
  }
}
