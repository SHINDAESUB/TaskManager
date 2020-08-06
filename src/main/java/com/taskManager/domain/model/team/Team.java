package com.taskManager.domain.model.team;

import com.taskManager.domain.model.AbstractBaseEntity;
import com.taskManager.domain.model.user.UserId;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "team")
public class Team extends AbstractBaseEntity {

  private static final long serialVersionUID = -2264390861852998965L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 128)
  private String name;

  @Column(name = "userId")
  private long userId;

  @Column(name = "archived")
  private boolean archived;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  /**
   * 팀 생성
   * 생성된 팀 정보에 사용자와 연결하기 위해서 UserId 인스턴스를 전달
   */
  public static Team create(String name, UserId creatorId) {
    Team team = new Team();
    team.name = name;
    team.archived = false;
    team.userId = creatorId.value();
    team.createdDate = new Date();
    return team;
  }

  public TeamId getId() {
    return new TeamId(id);
  }

  public String getName() {
    return name;
  }

  public UserId getUserId() {
    return new UserId(userId);
  }

  public boolean isArchived() {
    return archived;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Team)) return false;
    Team team = (Team) o;
    return userId == team.userId &&
      Objects.equals(name, team.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, userId);
  }

  @Override
  public String toString() {
    return "Team{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", userId=" + userId +
      ", archived=" + archived +
      ", createdDate=" + createdDate +
      '}';
  }
}