package com.taskManager.domin.common.event;

//DomainEvent.java 인터페이스
public interface DomainEventPublisher {
  void publish(DomainEvent event);
}
