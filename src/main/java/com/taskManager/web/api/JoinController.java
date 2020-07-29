package com.taskManager.web.api;

import javax.validation.Valid;

import com.taskManager.domain.application.service.UserService;
import com.taskManager.domain.model.user.exception.join.EmailAddressExistsException;
import com.taskManager.domain.model.user.exception.join.JoinException;
import com.taskManager.domain.model.user.exception.join.UsernameExistsException;
import com.taskManager.web.payload.JoinPayload;
import com.taskManager.web.result.ApiResult;
import com.taskManager.web.result.Result;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class JoinController {
    private UserService service;

    public JoinController(UserService  service){
      this.service = service;
    }

    /* 가입 API 구현부
     1.요청온 APi 를 JoinPayload 에서 데이터 검증
     2.@Valid 에노테이션이 있을 경우 데이터를 전달하기 전에 JoinPayload의 데이터 검증을 수행 시킨다.
     3.검증이 완료되면 JoinPayload 인스턴스를 가지고 JoinCommand 클래스로 변환한다. -- JoinPayload.toCommand()
     4.Serive 를
     5.Result.created() 정의 한 값으로 보냄
     */
    @PostMapping("/api/join")
    public ResponseEntity<ApiResult> join(@Valid @RequestBody JoinPayload payload) {
      try{
        service.join(payload.toCommand());
        return Result.created();
      } catch (JoinException e) {
        String errorMessage = "등록 실패";
        if (e instanceof UsernameExistsException) {
          errorMessage = "Username 존재하지 않음";
        } else if (e instanceof EmailAddressExistsException) {
          errorMessage = "Email address 존재하지 않음";
        }
        return Result.failure(errorMessage);
      }
    }
  }
