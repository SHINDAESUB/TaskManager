package com.taskManager.domain.model.board;

import javax.persistence.*;

import com.taskManager.domain.model.AbstractBaseEntity;
import com.taskManager.domain.model.team.TeamId;
import com.taskManager.domain.model.user.UserId;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "board")
public class Board extends AbstractBaseEntity {

  private static final long serialVersionUID = -7789177855101967490L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 128, unique = true)
  private String name;

  @Column(name = "description", nullable = false, length = 256, unique = true)
  private String description;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "team_id")
  private Long teamId;

  @Column(name = "archived")
  private boolean archived;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  /**
   * 게시판 생성
   * 생성된 게시판에 유저ID 와 팀ID를 연결하기 위해 인스턴스 생성
   */
  public static Board create(UserId userId, String name, String description, TeamId teamId) {
    Board board = new Board();
    board.userId = userId.value();
    board.name = name;
    board.description = description;
    board.teamId = teamId.isValid() ? teamId.value() : null;
    board.archived = false;
    board.createdDate = new Date();
    return board;
  }

  public BoardId getId() {
    return new BoardId(id);
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public UserId getUserId() {
    return new UserId(userId);
  }

  public TeamId getTeamId() {
    return teamId == null ? new TeamId(0) : new TeamId(teamId);
  }

  public boolean isPersonal() {
    return teamId == null;
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
    if (!(o instanceof Board)) return false;
    Board board = (Board) o;
    return userId == board.userId &&
      teamId == board.teamId &&
      Objects.equals(name, board.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, userId, teamId);
  }

  @Override
  public String toString() {
    return "Board{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", description='" + description + '\'' +
      ", userId=" + userId +
      ", teamId=" + teamId +
      ", archived=" + archived +
      ", createdDate=" + createdDate +
      '}';
  }
}