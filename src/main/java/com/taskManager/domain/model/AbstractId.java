package com.taskManager.domain.model;

import java.io.Serializable;
import java.util.Objects;

/** 
 * Team , User 의 추상 ID 
*/

public abstract class AbstractId implements Serializable {
    
  private static final long serialVersionUID = 3435210296634626689L;

  private long id;

  public AbstractId(long id) {
    this.id = id;
  }

  public long value() {
    return id;
  }

  public boolean isValid() {
    return id > 0;
  }

  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AbstractId)) return false;
    AbstractId that = (AbstractId) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
    
}