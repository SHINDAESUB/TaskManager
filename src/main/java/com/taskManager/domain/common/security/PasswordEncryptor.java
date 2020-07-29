package com.taskManager.domain.common.security;

public interface PasswordEncryptor {
  /**
   * password 복호화 
   */
  String encrypt(String rawPassword);
}
