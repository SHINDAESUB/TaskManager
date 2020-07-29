package com.taskManager.domin.model.user;

import com.taskManager.domin.model.AbstractBaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/** DB 와 ORM 한다.
 * 1. @Table(name ="DB테이블이름") :ORM 매핑
 * 2. @Id :PK 값
 * 3. GenerationType.IDENTITY  : id 필드 값이 DB의해 생성되는 것임을 하이버네트에게 아렬줌
 * 4. @Column(name = "DB필드이름"),  : 필드와 매핑
 * - 생략 가능 하지만 리파지토리 테스트 할떄는 임베디드 DB인 'H2' 생생하기 위해 hbm2ddl 기능엔 필수
*/

@Entity
@Table(name = "user")
public class User extends AbstractBaseEntity {

  private static final long serialVersionUID = -538781580460070724L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", nullable = false, length = 50, unique = true)
  private String username;

  @Column(name = "email_address", nullable = false, length = 100, unique = true)
  private String emailAddress;

  @Column(name = "password", nullable = false, length = 30)
  private String password;

  @Column(name = "first_name", nullable = false, length = 45)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 45)
  private String lastName;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  public User() {
  }

  /**
   * Create new user during registration
   */
  public static User create(final String username, final String emailAddress, final String password) {
    final User user = new User();
    user.username = username;
    user.emailAddress = emailAddress;
    user.password = password;
    user.firstName = "";
    user.lastName = "";
    user.createdDate = new Date();
    return user;
  }

  public void updateName(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getPassword() {
    return password;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    final User user = (User) o;
    return Objects.equals(username, user.username) &&
      Objects.equals(emailAddress, user.emailAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, emailAddress);
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", username='" + username + '\'' +
      ", emailAddress='" + emailAddress + '\'' +
      ", password=<Protected> " +
      ", firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", createdDate=" + createdDate +
      '}';
  }
}
