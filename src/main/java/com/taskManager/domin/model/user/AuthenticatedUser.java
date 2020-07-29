package com.taskManager.domin.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/** User 객체를 활용해서 생성된 읽기 전용이다. 
 *  username은 Authenticator.java 에 저장 되어 인증된 사용자가 누군지 알고 싶을떄 사용
*/
public class AuthenticatedUser implements UserDetails, Serializable {

    private static final long serialVersionUID = -7144174657188362966L;

    private long userId;
    private String username;
    private String password;
  
    public AuthenticatedUser(User user) {
      this.userId = user.getId();
      this.username = user.getUsername();
      this.password = user.getPassword();
    }
  
    public long getUserId() {
      return userId;
    }
  
    @Override
    public String getPassword() {
      return password;
    }
  
    @Override
    public String getUsername() {
      return username;
    }
  
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }
  
    @Override
    public boolean isAccountNonExpired() {
      return true;
    }
  
    @Override
    public boolean isAccountNonLocked() {
      return true;
    }
  
    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }
  
    @Override
    public boolean isEnabled() {
      return true;
    }
  
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof AuthenticatedUser)) return false;
      AuthenticatedUser that = (AuthenticatedUser) o;
      return Objects.equals(username, that.username);
    }
  
    @Override
    public int hashCode() {
      return Objects.hash(username);
    }
  
    @Override
    public String toString() {
      return "SimpleUser{" +
        "userId=" + userId +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        '}';
    }  
}