package com.taskManager.domin.model.user.event;

import com.taskManager.domin.common.event.DomainEvent;
import com.taskManager.domin.model.user.User;
import org.springframework.util.Assert;

public class JoinEvent extends DomainEvent {

  private static final long serialVersionUID = 2580061707540917880L;

  private User user;

  public JoinEvent(User user) {
    super(user);
    Assert.notNull(user, "`user` 파라미터는 null이 아닙니다.");
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    JoinEvent that = (JoinEvent) o;
    return that.user.equals(this.user);
  }

  public int hashCode() {
    return this.user.hashCode();
  }

  public String toString() {
    return "JoinEvent{" +
      "user='" + user + '\'' +
      "timestamp='" + getTimestamp() + '\'' +
      '}';
  }

}
