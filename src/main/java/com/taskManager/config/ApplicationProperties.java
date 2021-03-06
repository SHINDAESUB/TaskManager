package com.taskManager.config;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix="app")
@Validated
public class ApplicationProperties {

  /**
   * Default `from` value of emails sent out by the system
   */
  @Email
  @NotBlank
  private String mailFrom;

  @NotBlank
  @NotEmpty
  private String tokenSecretKey;

  @NotBlank
  @NotEmpty
  private String socketServerUrl;


  public void setMailFrom(String mailFrom) {
    this.mailFrom = mailFrom;
  }

  public String getMailFrom() {
    return mailFrom;
  }	
  public String getTokenSecretKey() {
    return tokenSecretKey;
  }

  public void setTokenSecretKey(String tokenSecretKey) {
    this.tokenSecretKey = tokenSecretKey;
  }

  public String getSocketServerUrl() {
    return socketServerUrl;
  }

  public void setSocketServerUrl(String socketServerUrl) {
    this.socketServerUrl = socketServerUrl;
  }
}