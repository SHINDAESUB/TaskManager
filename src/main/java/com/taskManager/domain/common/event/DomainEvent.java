package com.taskManager.domain.common.event;

import org.springframework.context.ApplicationEvent;

public abstract class DomainEvent extends ApplicationEvent {

  private static final long serialVersionUID = -444783093811334147L;

  public DomainEvent(Object source) {
    super(source);
  }

  //이벤트 발생 시간 얻기 위함
  public long occurredAt() {
    // 구현체의 타임스탬프(이벤트 발생 시간 얻기위함)을 반환한다
    return getTimestamp();
  }
}
