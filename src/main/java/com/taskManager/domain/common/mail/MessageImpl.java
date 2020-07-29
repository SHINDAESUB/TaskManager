package com.taskManager.domain.common.mail;
import java.util.Objects;

public class MessageImpl implements Message {

  private String to;
  private String subject;
  private String body;
  private String from;

  public MessageImpl(String to, String subject, String body, String from) {
    this.to = to;
    this.subject = subject;
    this.body = body;
    this.from = from;
  }

  @Override
  public String getTo() {
    return to;
  }

  @Override
  public String getSubject() {
    return subject;
  }

  @Override
  public String getBody() {
    return body;
  }

  public String getFrom() {
    return from;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MessageImpl)) return false;
    MessageImpl that = (MessageImpl) o;
    return Objects.equals(to, that.to) &&
      Objects.equals(subject, that.subject) &&
      Objects.equals(body, that.body);
  }

  @Override
  public int hashCode() {
    return Objects.hash(to, subject, body);
  }

  @Override
  public String toString() {
    return "MessageImpl{" +
      "to='" + to + '\'' +
      ", subject='" + subject + '\'' +
      ", body='" + body + '\'' +
      '}';
  }
}