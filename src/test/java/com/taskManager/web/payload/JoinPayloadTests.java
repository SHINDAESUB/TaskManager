package com.taskManager.web.payload;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

/* Payload == 데이터 검증이 제대로 되는지 확인하는 테스트 클래스 */
public class JoinPayloadTests {

  /* 데이터 검증 수행 */
  private Validator validator;

  /* 각 테스트 메소드가 실행되기 전에 수행되도록 */
  @Before
  public void setup(){
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  /* 비어있는 가입 폼을 테스트 함 */
  public void validate_blackPayload_fail(){
    JoinPayload payload = new JoinPayload();
    Set<ConstraintViolation<JoinPayload>> violations = validator.validate(payload);
    assertEquals(3, violations.size());
  }


  @Test
  public void validate_payloadWithEmailAddressLongerThan100_shouldFail() {
    // The maximium allowed localPart is 64 characters
    // http://www.rfc-editor.org/errata_search.php?rfc=3696&eid=1690
    int maxLocalParthLength = 64;
    String localPart = RandomStringUtils.random(maxLocalParthLength, true, true);
    int usedLength = maxLocalParthLength + "@".length() + ".com".length();
    String domain = RandomStringUtils.random(101 - usedLength, true, true);

    JoinPayload payload = new JoinPayload();
    payload.setEmailAddress(localPart + "@" + domain + ".com");
    payload.setUsername("MyUsername");
    payload.setPassword("MyPassword");

    Set<ConstraintViolation<JoinPayload>> violations = validator.validate(payload);
    assertEquals(1, violations.size());
  }

  @Test
  public void validate_payloadWithUsernameShorterThan2_shouldFail() {
    JoinPayload payload = new JoinPayload();
    String usernameTooShort = RandomStringUtils.random(1);
    payload.setUsername(usernameTooShort);
    payload.setPassword("MyPassword");
    payload.setEmailAddress("sunny@taskagile.com");

    Set<ConstraintViolation<JoinPayload>> violations = validator.validate(payload);
    assertEquals(1, violations.size());
  }

  @Test
  public void validate_payloadWithUsernameLongerThan50_shouldFail() {
    JoinPayload payload = new JoinPayload();
    String usernameTooLong = RandomStringUtils.random(51);
    payload.setUsername(usernameTooLong);
    payload.setPassword("MyPassword");
    payload.setEmailAddress("sunny@taskagile.com");

    Set<ConstraintViolation<JoinPayload>> violations = validator.validate(payload);
    assertEquals(1, violations.size());
  }

  @Test
  public void validate_payloadWithPasswordShorterThan6_shouldFail() {
    JoinPayload payload = new JoinPayload();
    String passwordTooShort = RandomStringUtils.random(5);
    payload.setPassword(passwordTooShort);
    payload.setUsername("MyUsername");
    payload.setEmailAddress("sunny@taskagile.com");

    Set<ConstraintViolation<JoinPayload>> violations = validator.validate(payload);
    assertEquals(1, violations.size());
  }

  @Test
  public void validate_payloadWithPasswordLongerThan30_shouldFail() {
    JoinPayload payload = new JoinPayload();
    String passwordTooLong = RandomStringUtils.random(31);
    payload.setPassword(passwordTooLong);
    payload.setUsername("MyUsername");
    payload.setEmailAddress("sunny@taskagile.com");

    Set<ConstraintViolation<JoinPayload>> violations = validator.validate(payload);
    assertEquals(1, violations.size());
  }


}
