package com.taskManager.domin.common.security;

import org.springframework.stereotype.Component;

  /**
   * password 복호화 구현
   * 
   */

@Component
public class PasswordEncryptorImpl implements PasswordEncryptor {

  @Override
  public String encrypt(String rawPassword) {

    return rawPassword;
  }
}
