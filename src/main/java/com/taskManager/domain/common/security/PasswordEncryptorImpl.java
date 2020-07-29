package com.taskManager.domain.common.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** 암호 복호화 */
@Component
public class PasswordEncryptorImpl implements PasswordEncryptor {

  private PasswordEncoder passwordEncoder;

  public PasswordEncryptorImpl(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String encrypt(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }
}
