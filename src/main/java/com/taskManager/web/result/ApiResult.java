package com.taskManager.web.result;

import java.util.HashMap;

import org.springframework.util.Assert;

public class ApiResult extends HashMap<String , Object> {

  private static final long serialVersionUID = 877825499039674411L;

  private static final String MESSAGE_KEY = "message";
  private static final String ERROR_CODE_KEY = "errorReferenceCode";

  public static ApiResult blank() {
    return new ApiResult();
  }

  public static ApiResult message(String message) {
    Assert.hasText(message, "파라미터 `message` 비워 둘수 없음");

    ApiResult apiResult = new ApiResult();
    apiResult.put("message", message);
    return apiResult;
  }

  public static ApiResult error(String message, String errorReferenceCode) {
    Assert.hasText(message, "파라미터 `message` 비워 둘수 없음");
    Assert.hasText(errorReferenceCode, "파라미터 `errorReferenceCode` 비워 둘수 없음");

    ApiResult apiResult = new ApiResult();
    apiResult.put(MESSAGE_KEY, message);
    apiResult.put(ERROR_CODE_KEY, errorReferenceCode);
    return apiResult;
  }

  public ApiResult add(String key, Object value) {
    Assert.hasText(key, "파라미터 `key`  비워 둘수 없음");
    Assert.notNull(value, "파라미터 `value` null이 될 수 없음");

    this.put(key, value);
    return this;
  }

}
