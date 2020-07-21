package com.taskManager.web.result;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

/*결과값 */
public final class Result {

  private Result() {
  }

  public static ResponseEntity<ApiResult> created() {
    return ResponseEntity.status(201).build();
  }

  public static ResponseEntity<ApiResult> ok() {
    return ResponseEntity.ok().build();
  }

  public static ResponseEntity<ApiResult> ok(String message) {
    Assert.hasText(message, "파라미터 `message` 비워 둘수 없음");

    return ok(ApiResult.message(message));
  }

  public static ResponseEntity<ApiResult> ok(ApiResult payload) {
    Assert.notNull(payload, "파라미터 `payload` null 값이 될 수 없음");

    return ResponseEntity.ok(payload);
  }

  public static ResponseEntity<ApiResult> failure(String message) {
    return ResponseEntity.badRequest().body(ApiResult.message(message));
  }

  public static ResponseEntity<ApiResult> serverError(String message, String errorReferenceCode) {
    return ResponseEntity.status(500).body(ApiResult.error(message, errorReferenceCode));
  }

  public static ResponseEntity<ApiResult> notFound() {
    return ResponseEntity.notFound().build();
  }

  public static ResponseEntity<ApiResult> unauthenticated() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  public static ResponseEntity<ApiResult> forbidden() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }
}