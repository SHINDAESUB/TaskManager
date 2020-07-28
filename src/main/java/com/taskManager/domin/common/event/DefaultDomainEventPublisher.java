package com.taskManager.domin.common.event;

import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationEventPublisher;

/* 도메인 이벤트 과정
*  1. DomainEventPublisher 에서 도메인 이벤트 발생하는 publish() 메소드 생성
*  2. publish(DomainEvent event) 매개변수는 DomainEvent.java다
*  - DomainEvent.java :
*    Spring의 ApplicationEvent 를 상속 받아 도메인 이벤트 구현
*    ApplicationEvent를 이용하여 도메인 이벤트를 수신/발신 할 수 있다.
*  3. DefaultDomainEventPublisher 는 도메인 이벤트의 구현부
*  - 실제 이벤트의 발행하는 인스턴스 ApplicationEventPublisher 주입 후 이벤트를 발행 작업
*/

@Component
public class DefaultDomainEventPublisher implements DomainEventPublisher {

  private ApplicationEventPublisher actualPublisher;

  public DefaultDomainEventPublisher(ApplicationEventPublisher actualPublisher) {
    this.actualPublisher = actualPublisher;
  }

  @Override
  public void publish(DomainEvent event) {
    actualPublisher.publishEvent(event);
  }
}
